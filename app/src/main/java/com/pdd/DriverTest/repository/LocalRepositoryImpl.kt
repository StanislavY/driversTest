package com.pdd.DriverTest.repository

import com.pdd.DriverTest.app.domain.model.ListItem
import com.pdd.DriverTest.datasource.Room.DataBaseFromFB.DatabaseDAO
import com.pdd.DriverTest.datasource.Room.DataBaseFromFB.DatabaseEntity
import com.pdd.DriverTest.datasource.Room.DatabaseResult.ResultEntity
import com.pdd.DriverTest.app.domain.usecase.Converters

class LocalRepositoryImpl(private val localDataSource: DatabaseDAO) : LocalRepository {
    private val converter: Converters = Converters()
    override fun putDataFromServer1CToLocalDatabase(listItemFromServer: List<ListItem>) {
       for (mainList in listItemFromServer) {
            val data=DatabaseEntity(mainList.collection,mainList.documentFB,mainList.field,mainList.value,"","")
            insertToDB(data)

          }

    }
    override suspend fun putDataFromFBToLocalDatabase(listItemFromServer: List<ListItem>) {
        for (mainList in listItemFromServer) {

            val data=DatabaseEntity(mainList.collection,mainList.documentFB,mainList.field,mainList.value,mainList.theme,mainList.typeOftest)
            insertToDB(data)

        }

    }

    override fun insertToDB(data:DatabaseEntity){
        localDataSource.insert(data)


    }

    override fun getAllDataDB1CEntity():List<ListItem> {
        return converter.convertEntityDB1CToMainList(localDataSource.all1C())

    }

    override fun deleteAllData(){
        localDataSource.deleteall()
    }

    override fun getAllUnfinishedDataDBResultEntityToMainList(): List<ListItem> {
        return converter.convertEntityResultToMainList(localDataSource.getAllUnfinishedResult())
    }

    override fun putDataToResultDBFromListItem(resultListItem: List<ListItem>) {
        val data=converter.convertRemListToResultEntity(resultListItem)
        for (mainList in data) {

            insertToDBResultFromResultEntity(mainList)

        }
    }

    override fun getAllUnfinishedDataDBResultEntity(): List<ResultEntity> {
        return localDataSource.getAllUnfinishedResult()
    }

    override fun insertToDBResultFromResultEntity(data: ResultEntity){
        localDataSource.insertDataToResult(data)


    }

    override fun getAllDatafromDBResult(): List<ResultEntity> {
      return  localDataSource.allFromResultDB()

    }


}

