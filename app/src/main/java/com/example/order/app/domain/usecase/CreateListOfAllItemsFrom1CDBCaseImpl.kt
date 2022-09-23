package com.example.order.app.domain.usecase

import com.example.order.app.domain.model.ListItem
import com.example.order.core.App
import com.example.order.core.GlobalConstAndVars
import com.example.order.repository.LocalRepository
import com.example.order.repository.LocalRepositoryImpl

class CreateListOfAllItemsFrom1CDBCaseImpl: CreateListOfAllItemsFrom1CDBCase {

    private val localRepository1C: LocalRepository = LocalRepositoryImpl(App.get1CDAO())


    override suspend fun getListForChoice(): List<ListItem> {
        val startList: List<ListItem>

        val dataFrom1C: List<ListItem> = localRepository1C.getAllDataDB1CEntity()
        GlobalConstAndVars.listItemFromDb=dataFrom1C
        startList=dataFrom1C
        GlobalConstAndVars.GLOBAL_LIST=startList

        return startList
    }

}










