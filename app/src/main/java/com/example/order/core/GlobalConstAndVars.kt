package com.example.order.core

import com.example.order.app.domain.model.ListItem
import com.example.order.datasource.Server.ServerResponseData

object GlobalConstAndVars {
    val DEFAULT_LIST_ITEM=ListItem("","","","","","")
    var ANSWER_CLICKED:ListItem= DEFAULT_LIST_ITEM
    var CHOSEN_LIST_ITEM= DEFAULT_LIST_ITEM
    const val NAME_VARIANT_FIELD="variant"
    const val NAME_ANSWER_FIELD="answer"
    const val NAME_QUESTION_FIELD="question"
    const val START_TICKET="1"
    var SWITCH=1
    var TICKETS_LIST:List<ListItem> = mutableListOf()
    var QUESTIONS_LIST:List<ListItem> = mutableListOf()
    var QUESTION_TEXT_LIST:List<ListItem> = mutableListOf()
    var RIGHT_ANSWER:ListItem=ListItem("","","","","","")
    var QUESTION_TEXT=""
    var IMAGE_URL_NAME="picture"
    var PICTURES_URL=""
    var URL_LIST:List<ListItem> = mutableListOf()
    var ANSWER_NUMBER=""
    var ANSWER_LIST:List<ListItem> = mutableListOf()
    var listItemFromDb:List<ListItem> = mutableListOf()
    var LIST_OF_CHOSEN_ITEMS: MutableList<ListItem> = mutableListOf()
    var DATABASE1C_NAME: String = "Database1C.db"
    const val STEP_FOR_WORKED_HOURS=1.0
    const val NUMBERS_OF_VALUES_FOR_WORKED_HOURS=50
    val DEFAULT_lIST= listOf(ListItem("","","","","",""))

    var GLOBAL_LIST= DEFAULT_lIST
    const val DEFAULT_VALUE_FOR_GENERATED_LIST=""
    var LIST_OF_FINISHED_ORDERS = listOf<ServerResponseData>()
    const val MARKER_OF_FINISHED_ORDER="1"

}