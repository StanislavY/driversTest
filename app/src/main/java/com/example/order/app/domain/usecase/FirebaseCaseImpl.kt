package com.example.order.app.domain.usecase

import android.content.ContentValues.TAG
import android.util.Log
import com.example.order.app.domain.model.ListItem
import com.example.order.core.App
import com.example.order.core.GlobalConstAndVars
import com.example.order.app.domain.model.ServerResponseDataFireBase
import com.example.order.datasource.Room.DataBaseFrom1C.DatabaseFrom1CEntity
import com.example.order.datasource.fireBase.Task
import com.example.order.repository.LocalRepository
import com.example.order.repository.LocalRepositoryImpl
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
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

            var listItems: MutableList<ListItem> = mutableListOf()
            var resumed = false




            remoteDB.collection(collectionsName)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        var list = converters.mapDocumentToServerResponseDataFireBase(
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




                        /* database.insertToDB(converters.mapDocumentToRemoteTaskHM(document,collectionsName))
                    database.insertToDB(converters.mapToDB(converters.mapDocumentToRemoteTask(document)))
                    listFromCloud.add(converters.mapDocumentToRemoteTask(document))*/

                    }

                }
                .addOnFailureListener { exception ->

                    Log.d(TAG, "Error getting documents: ", exception)
                    if (!resumed) {
                        it.resumeWithException(exception)
                        resumed = true
                    }


                }
            /* appCoroutineScope.launch { delay(5000)  }*/
        }













    }

    private val appCoroutineScope = CoroutineScope(
        Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, _ ->
            handleError()
        })

    private fun handleError() {}

    fun getAllTask(): Single<List<DatabaseFrom1CEntity>> {
        var x: List<com.example.order.datasource.fireBase.Task> = listOf()
        return Single.create<List<DocumentSnapshot>> { emitter ->
            remoteDB.collection("test")
                .get()
                .addOnSuccessListener {
                    if (!emitter.isDisposed) {
                        emitter.onSuccess(it.documents)
                    }
                }
                .addOnFailureListener {
                    if (!emitter.isDisposed) {
                        emitter.onError(it)
                    }
                }
        }
            .observeOn(Schedulers.io())
            .flatMapObservable { Observable.fromIterable(it) }
            .map(::mapDocumentToRemoteTask)
            .map(::mapToTask)
            .toList()

    }
    fun ff(ddd:Single<List<DatabaseFrom1CEntity>>){

    }


    private fun mapToTask(remoteTask: ServerResponseDataFireBase): DatabaseFrom1CEntity {


        return DatabaseFrom1CEntity(

            remoteTask.collection,
            remoteTask.documentFB,
            remoteTask.field,
            value = remoteTask.value,
            theme = remoteTask.theme,
            typeOfTests = remoteTask.typeOfTests
        )
    }

    private fun mapDocumentToRemoteTask(document: DocumentSnapshot) = document.toObject(ServerResponseDataFireBase::class.java)!!
}




/* var conv:ServerResponseDataFireBase?
      var list:MutableList<ServerResponseDataFireBase?> = mutableListOf()
      var taskList:List<DocumentSnapshot> = listOf()
      var mes:String

      var x1=0
      var x=0
      remoteDB.collection("test")
      .get()
          .addOnSuccessListener { querySnapshot ->
              taskList= querySnapshot.documents
              GlobalConstAndVars.taskList=taskList


              // Успешно получили данные. Список в querySnapshot.documents
          }



          .addOnFailureListener { exception ->
             mes= exception.message.toString()


              // Произошла ошибка при получении данных
          }
      for (documentSnapshot in taskList) {
         documentSnapshot.toObject(ServerResponseDataFireBase::class.java)


      }

*/


/*
private fun mapToTask(remoteTask: ServerResponseDataFireBase): com.example.order.datasource.fireBase.Task {


    return Task(

        remoteTask.variant1,
        remoteTask.variant2,
        remoteTask.variant3,
        remoteTask.question,
        remoteTask.picture,
        remoteTask.answer
    )
}
*/






