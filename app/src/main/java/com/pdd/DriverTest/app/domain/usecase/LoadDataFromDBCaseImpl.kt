package com.pdd.DriverTest.app.domain.usecase

import com.pdd.DriverTest.repository.LocalRepository
import com.pdd.DriverTest.repository.LocalRepositoryImpl
import com.pdd.DriverTest.app.domain.model.ListItem
import com.pdd.DriverTest.core.App

class LoadDataFromDBCaseImpl:LoadDataFromDBCase {
    private val localRepository:LocalRepository=LocalRepositoryImpl(App.get1CDAO())
    override fun executeDeletingDataFromDb() {
        localRepository.deleteAllData()
    }

    override fun executeDownloadingDataFrom1CToDB(listItemFromServer:List<ListItem>) {
        localRepository.putDataFromServer1CToLocalDatabase(listItemFromServer)
    }

    override suspend fun executeDownloadingDataFromFireBaseToLocalDB(listItemFromFB: List<ListItem>) {
        localRepository.putDataFromFBToLocalDatabase(listItemFromFB)
    }
}