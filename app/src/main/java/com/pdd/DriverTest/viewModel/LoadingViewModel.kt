package com.pdd.DriverTest.viewModel

import androidx.lifecycle.ViewModel
import com.pdd.DriverTest.app.domain.usecase.CreateListOfAllItemsFromDBCase
import com.pdd.DriverTest.app.domain.usecase.CreateListOfAllItemsFromDBCaseImpl

class LoadingViewModel(
):ViewModel() {
    private val createGlobalListCase: CreateListOfAllItemsFromDBCase = CreateListOfAllItemsFromDBCaseImpl()
      suspend fun getGlobalLIst()
      {
        createGlobalListCase.getListForChoice()

    }


}