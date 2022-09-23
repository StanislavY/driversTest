package com.example.order.app.domain.usecase

import android.content.ContentValues.TAG
import android.util.Log
import com.example.order.app.domain.model.ListItem
import com.example.order.core.App
import com.example.order.repository.LocalRepository
import com.example.order.repository.LocalRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseCaseImpl:FireBaseCase {
    private val database: LocalRepository = LocalRepositoryImpl(App.get1CDAO())
    private val converters: Converters = Converters()

    private val remoteDB = FirebaseFirestore.getInstance().apply {
        firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .build()
    }


    override suspend fun executeGettingDataFromFirebase(collectionsName: String): MutableList<ListItem> {

        return suspendCoroutine {

            val listItems: MutableList<ListItem> = mutableListOf()
            var resumed = false

            remoteDB.collection(collectionsName)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        val list = converters.mapDocumentToServerResponseDataFireBase(
                            document,
                            collectionsName
                        )

                        for (serverResponseDataFireBase in list) {
                            database.insertToDB(converters.mapServerResponseDataFireBaseToDBEntity(serverResponseDataFireBase))
                            listItems.add(
                                converters.mapServerResponseDataFireBaseToListItem(
                                    serverResponseDataFireBase
                                )
                            )
                        }
                        AppState.Success(listItems)
                        if (!resumed) {
                            it.resume(listItems)
                            resumed = true
                        }
                    }

                }
                .addOnFailureListener { exception ->

                    Log.d(TAG, "Error getting documents: ", exception)
                    if (!resumed) {
                        it.resumeWithException(exception)
                        resumed = true
                    }


                }

        }













    }


}









