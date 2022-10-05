package com.pdd.DriverTest.app.domain.usecase

import com.pdd.DriverTest.app.domain.model.ListItem
import com.pdd.DriverTest.core.App
import com.pdd.DriverTest.core.GlobalConstAndVars
import com.pdd.DriverTest.repository.LocalRepository
import com.pdd.DriverTest.repository.LocalRepositoryImpl

class CreateListOfAllItemsFromDBCaseImpl: CreateListOfAllItemsFromDBCase {

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










