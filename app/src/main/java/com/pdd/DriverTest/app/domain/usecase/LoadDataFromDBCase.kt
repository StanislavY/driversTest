package com.pdd.DriverTest.app.domain.usecase

import com.pdd.DriverTest.app.domain.model.ListItem

interface LoadDataFromDBCase {
   fun  executeDeletingDataFromDb()
   fun executeDownloadingDataFrom1CToDB(listItemFromServer:List<ListItem>)
   suspend fun executeDownloadingDataFromFireBaseToLocalDB(listItemFromFB:List<ListItem>)
}