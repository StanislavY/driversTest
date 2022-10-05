package com.pdd.DriverTest.repository

import com.pdd.DriverTest.app.domain.model.ListItem
import com.pdd.DriverTest.datasource.Room.DataBaseFromFB.DatabaseEntity
import com.pdd.DriverTest.datasource.Room.DatabaseResult.ResultEntity

interface LocalRepository {
    fun putDataFromServer1CToLocalDatabase(listItemFromServer:List<ListItem>)
    fun getAllDataDB1CEntity(): List<ListItem>
    fun deleteAllData()
    fun getAllUnfinishedDataDBResultEntityToMainList():List<ListItem>
    fun putDataToResultDBFromListItem(resultListItem:List<ListItem>)
    fun getAllUnfinishedDataDBResultEntity():List<ResultEntity>
    fun insertToDBResultFromResultEntity(data: ResultEntity)
    fun getAllDatafromDBResult():List<ResultEntity>
    fun insertToDB(data: DatabaseEntity)
    suspend fun putDataFromFBToLocalDatabase(listItemFromServer: List<ListItem>)


}