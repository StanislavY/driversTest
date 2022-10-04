package com.example.order.viewModel

import androidx.lifecycle.ViewModel
import com.example.order.app.domain.usecase.CreateListOfAllItemsFromDBCase
import com.example.order.app.domain.usecase.CreateListOfAllItemsFromDBCaseImpl

class LoadingViewModel(
):ViewModel() {
    private val createGlobalListCase: CreateListOfAllItemsFromDBCase = CreateListOfAllItemsFromDBCaseImpl()
      suspend fun getGlobalLIst()
      {
        createGlobalListCase.getListForChoice()

    }


}