package com.example.order.datasource.Room.DataBaseFromFB

import androidx.room.Entity


@Entity(primaryKeys = ["id1","dataType","ticketNumber"])
data class DatabaseEntity(

    var id1: String,
    var dataType: String,
    var ticketNumber:String,
    var value:String,
    var theme:String,
    var typeOfTests:String


)