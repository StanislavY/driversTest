package com.example.order.app.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.order.core.GlobalConstAndVars
import com.example.order.app.domain.model.ListItem
import com.example.order.repository.LocalRepository
import com.example.order.repository.LocalRepositoryImpl
import com.example.order.datasource.Room.DatabaseResult.ResultEntity
import com.example.order.core.App

class GetSelectionResultCaseImpl: GetSelectionResultCase {
    private val localDataSource: LocalRepository = LocalRepositoryImpl(App.get1CDAO())

    @RequiresApi(Build.VERSION_CODES.N)
    override fun rememberListOfChosenItems(listItem: ListItem): MutableList<ListItem> {
        val rememberedListItem:MutableList<ListItem> = GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS
        val iterator=rememberedListItem.iterator()
        while (iterator.hasNext()) {
            val item=iterator.next()
            if (item.collection == listItem.collection) {
                iterator.remove()
            }
        }
        rememberedListItem.add(listItem)
        GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS=rememberedListItem
        return GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS
    }


    override fun putListOfChosenItemToDB(data:MutableList<ListItem>) {
        localDataSource.putDataToResultDBFromListItem(data)

    }

    override fun getAllDataDBResultEntityToListItem():List<ListItem> {
        return localDataSource.getAllUnfinishedDataDBResultEntityToMainList()
    }

    override fun isAnswerRight(listForChecking: List<ListItem>) {
        for (listItem in listForChecking) {
            for (listOfChosenItem in GlobalConstAndVars.LIST_OF_CHOSEN_ITEMS) {
                listItem.value=listOfChosenItem.value

            }

        }
    }


}


