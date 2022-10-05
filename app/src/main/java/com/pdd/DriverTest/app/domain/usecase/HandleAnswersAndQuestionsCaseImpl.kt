package com.pdd.DriverTest.app.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.pdd.DriverTest.core.GlobalConstAndVars
import com.pdd.DriverTest.app.domain.model.ListItem
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HandleAnswersAndQuestionsCaseImpl: HandleAnswersAndQuestionsCase {
    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun getTicketsList(key: String): List<ListItem> {
          return suspendCoroutine { res ->
              val ticketList=GlobalConstAndVars.GLOBAL_LIST.distinctBy { it.documentFB }
              GlobalConstAndVars.SWITCH=1
              GlobalConstAndVars.TICKETS_LIST=ticketList
              res.resume(ticketList)

          }

    }

    override suspend fun getQuestionsAndAnswers(fieldsName: String, ticketNumber:String): List<ListItem> {
        return suspendCoroutine { res ->
            val listForFilter=GlobalConstAndVars.GLOBAL_LIST
            val filteredList= listForFilter.filter { it.documentFB==ticketNumber }.filter { it.field.contains(fieldsName)  }
            GlobalConstAndVars.SWITCH=2
            if (fieldsName == GlobalConstAndVars.NAME_VARIANT_FIELD) {
                GlobalConstAndVars.QUESTIONS_LIST=filteredList
            }
            if (fieldsName == GlobalConstAndVars.NAME_QUESTION_FIELD) {
                GlobalConstAndVars.QUESTION_TEXT_LIST=filteredList
                filteredList.forEach {
                    if (it.field == fieldsName) {
                        GlobalConstAndVars.QUESTION_TEXT=it.value

                    } }

            }
            if (fieldsName == GlobalConstAndVars.IMAGE_URL_NAME) {
                GlobalConstAndVars.URL_LIST=filteredList
                filteredList.forEach {
                    if (it.field == fieldsName) {
                        GlobalConstAndVars.PICTURES_URL=it.value

                    } }

            }
            if (fieldsName == GlobalConstAndVars.NAME_ANSWER_FIELD) {
                GlobalConstAndVars.ANSWER_LIST=filteredList
                filteredList.forEach {
                    if (it.field == fieldsName) {
                        GlobalConstAndVars.ANSWER_NUMBER=it.value

                    } }

            }

           

            res.resume(filteredList)
        }








    }

    override suspend fun isAnswerRight(rightAnswer: String, answerForCheck: String):Boolean {

        return answerForCheck.contains(rightAnswer)



    }
    override suspend fun detectRightAnswerFromList (list:List<ListItem>,rigtAnswerNumber:String){

        list.forEach {
            if (it.field.contains(rigtAnswerNumber)) {
                GlobalConstAndVars.RIGHT_ANSWER=it
            }



            }


    }






}








