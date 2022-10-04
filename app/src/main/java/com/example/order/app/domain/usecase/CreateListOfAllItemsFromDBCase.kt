package com.example.order.app.domain.usecase

import com.example.order.app.domain.model.ListItem

interface CreateListOfAllItemsFromDBCase {
   suspend fun getListForChoice():List<ListItem>




}