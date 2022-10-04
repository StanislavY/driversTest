package com.example.order.app.domain.usecase

import com.example.order.app.domain.model.ListItem


interface HandleAnswersAndQuestionsCase {
   suspend fun getTicketsList(key: String):List<ListItem>
   suspend fun getQuestionsAndAnswers(fieldsName: String, ticketNumber:String): List<ListItem>
   suspend fun isAnswerRight(rightAnswer:String,answerForCheck:String):Boolean
   suspend fun detectRightAnswerFromList (list:List<ListItem>,rigtAnswerNumber:String)



}