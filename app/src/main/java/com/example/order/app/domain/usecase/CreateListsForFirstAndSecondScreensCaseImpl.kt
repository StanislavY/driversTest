package com.example.order.app.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.order.core.GlobalConstAndVars
import com.example.order.app.domain.model.ListItem
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CreateListsForFirstAndSecondScreensCaseImpl: CreateListsForFirstAndSecondScreensCase {
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
            var listForFilter=GlobalConstAndVars.GLOBAL_LIST
            var filteredList= listForFilter.filter { it.documentFB==ticketNumber }.filter { it.field.contains(fieldsName)  }
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
           

            res.resume(filteredList)
        }






    }



}








