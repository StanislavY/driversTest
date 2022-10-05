package com.pdd.DriverTest.app.domain.usecase

import com.pdd.DriverTest.app.domain.model.ListItem

interface CreateListOfAllItemsFromDBCase {
   suspend fun getListForChoice():List<ListItem>




}