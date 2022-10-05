package com.pdd.DriverTest.app.domain.usecase

import androidx.lifecycle.ViewModel
import com.pdd.DriverTest.app.domain.model.ListItem
import com.pdd.DriverTest.app.domain.model.ServerResponseDataFireBase
import com.pdd.DriverTest.datasource.Room.DataBaseFromFB.DatabaseEntity
import com.pdd.DriverTest.datasource.Room.DatabaseResult.ResultEntity
import com.google.firebase.firestore.DocumentSnapshot
import java.util.*

open class Converters : ViewModel() {

    fun convertEntityDB1CToMainList(entityList: List<DatabaseEntity>): List<ListItem> {
        return entityList.map {
            ListItem(it.id1, it.dataType, it.ticketNumber, it.value,it.theme,it.dataType)

        }


    }


    fun mapServerResponseDataFireBaseToListItem(remoteTask: ServerResponseDataFireBase): ListItem {


        return ListItem(
            remoteTask.collection,
            remoteTask.documentFB,
            remoteTask.field,
            value = remoteTask.value,
            theme = remoteTask.theme,
            remoteTask.typeOfTests

        )
    }

    fun mapServerResponseDataFireBaseToDBEntity(remoteTask: ServerResponseDataFireBase): DatabaseEntity {


        return DatabaseEntity(
            remoteTask.collection,
            remoteTask.documentFB,
            remoteTask.field,
            value = remoteTask.value,
            theme = remoteTask.theme,
            typeOfTests = remoteTask.typeOfTests

        )
    }

    fun mapDocumentToServerResponseDataFireBase(
        document: DocumentSnapshot,
        collection: String
    ): MutableList<ServerResponseDataFireBase> {
        val result: MutableList<ServerResponseDataFireBase> = mutableListOf()
        var themeForList =""
        var typeOfTestDB =""

        val hashMap = document.data
        if (hashMap != null) {
            for (i in hashMap) {
                val item = ServerResponseDataFireBase("", "", "", "")
                item.collection = collection
                item.documentFB = document.id
                if (i.key.toString() == "part") {
                    typeOfTestDB=i.value.toString()

                }
                if (i.key.toString() == "theme") {
                    themeForList=i.value.toString()
                }

                else {
                    item.field = i.key.toString()
                    item.value = i.value.toString()
                    result.add(item)
                }
                for (serverResponseDataFireBase in result) {
                    serverResponseDataFireBase.theme=themeForList
                    serverResponseDataFireBase.typeOfTests=typeOfTestDB

                }


            }

        }
        return result
    }


    fun convertEntityResultToMainList(entityList: List<ResultEntity>): List<ListItem> {
        return entityList.map { ListItem(it.id1, it.id2, it.name, it.uid,"","") }
    }

    fun convertRemListToResultEntity(remListItem: List<ListItem>): List<ResultEntity> {
        val uid = UUID.randomUUID().toString()
        return remListItem.map {
            ResultEntity(
                it.collection, it.documentFB, it.field, it.value, uid

            )
        }

    }

}










