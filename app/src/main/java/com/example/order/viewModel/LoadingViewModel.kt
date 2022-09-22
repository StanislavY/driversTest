package com.example.order.viewModel

import androidx.lifecycle.ViewModel
import com.example.order.app.domain.usecase.CreateListOfAllItemsFrom1CDBCase
import com.example.order.app.domain.usecase.CreateListOfAllItemsFrom1CDBCaseImpl

class LoadingViewModel(
):ViewModel() {
    private val createGlobalListCase: CreateListOfAllItemsFrom1CDBCase = CreateListOfAllItemsFrom1CDBCaseImpl()
      suspend fun getGlobalLIst()
      {
        createGlobalListCase.getListForChoice()

    }


}