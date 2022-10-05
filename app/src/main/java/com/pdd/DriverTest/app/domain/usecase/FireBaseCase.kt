package com.pdd.DriverTest.app.domain.usecase

import com.pdd.DriverTest.app.domain.model.ListItem

interface FireBaseCase{
  suspend fun executeGettingDataFromFirebase(collectionsName:String):MutableList<ListItem>
}