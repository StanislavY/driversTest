package com.example.order.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.order.app.domain.model.ListItem
import com.example.order.app.domain.usecase.AppState
import com.example.order.app.domain.usecase.HandleAnswersAndQuestionsCase
import com.example.order.app.domain.usecase.HandleAnswersAndQuestionsCaseImpl

open class MainViewModel(
    private val createLists: HandleAnswersAndQuestionsCase = HandleAnswersAndQuestionsCaseImpl(),
   ) : ViewModel() {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    suspend fun processTheSelectedItemTicket() = requestDataTicket()
    suspend fun processTheSelectedItemQuestion(fieldsName: String, ticketNumber: String) =
        requestDataQuestion(fieldsName, ticketNumber)
      private suspend fun requestDataTicket() {
       liveDataToObserve.postValue(
            AppState.SuccessTickets(createLists.getTicketsList(""))
        )
    }

    private suspend fun requestDataQuestion(fieldsName: String, ticketNumber: String) {
        liveDataToObserve.postValue(
            AppState.SuccessQuestions(createLists.getQuestionsAndAnswers(fieldsName, ticketNumber))
        )

    }

    suspend fun getQuestionsAndAnswers(fieldsName: String, ticketNumber: String): List<ListItem> {
            return createLists.getQuestionsAndAnswers(fieldsName, ticketNumber)
        }
    suspend fun isAnswerRight(rightAnswer: String, answerForCheck: String):Boolean {
        return createLists.isAnswerRight(rightAnswer,answerForCheck)
    }
    suspend fun detectRightAnswerFromList (list:List<ListItem>,rigtAnswerNumber:String){
        createLists.detectRightAnswerFromList(list,rigtAnswerNumber)
    }



    }




