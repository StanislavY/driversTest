package com.example.order.app.domain.usecase

import com.example.order.app.domain.model.ListItem

sealed class AppState {
    data class Success(val listItem: List<ListItem>,): AppState()
    data class SuccessTickets(val tickets:List<ListItem>):AppState()
    data class SuccessQuestions( val questions:List<ListItem>):AppState()

}