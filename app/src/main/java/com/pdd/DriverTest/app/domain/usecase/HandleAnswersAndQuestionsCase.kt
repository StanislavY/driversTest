package com.pdd.DriverTest.app.domain.usecase

import com.pdd.DriverTest.app.domain.model.ListItem


interface HandleAnswersAndQuestionsCase {
   suspend fun getTicketsList(key: String):List<ListItem>
   suspend fun getQuestionsAndAnswers(fieldsName: String, ticketNumber:String): List<ListItem>
   suspend fun isAnswerRight(rightAnswer:String,answerForCheck:String):Boolean
   suspend fun detectRightAnswerFromList (list:List<ListItem>,rigtAnswerNumber:String)



}