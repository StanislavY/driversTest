package com.example.order.datasource.Room.DataBaseFromFB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.order.datasource.Room.DatabaseResult.ResultEntity

@Database(entities = [DatabaseEntity::class, ResultEntity::class],version=2,exportSchema=false)
abstract class Database :RoomDatabase() {
    abstract fun databaseFrom1CDAO(): DatabaseDAO

}