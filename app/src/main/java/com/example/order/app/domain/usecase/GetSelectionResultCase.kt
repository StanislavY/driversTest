package com.example.order.app.domain.usecase

import com.example.order.app.domain.model.ListItem

interface GetSelectionResultCase {
    fun rememberListOfChosenItems(listItem: ListItem):MutableList<ListItem>

    fun putListOfChosenItemToDB(data:MutableList<ListItem>)
    fun getAllDataDBResultEntityToListItem():List<ListItem>
    fun isAnswerRight(listForChecking:List<ListItem>)



}
