package com.example.order.datasource.Room.DataBaseFromFB

import android.database.Cursor
import androidx.room.*
import com.example.order.datasource.Room.DatabaseResult.ResultEntity

@Dao
interface DatabaseDAO {

        @Query("SELECT*FROM DatabaseEntity")
        fun all1C():List<DatabaseEntity>


        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(entity: DatabaseEntity)

        @Update
        fun update(entity: DatabaseEntity)

        @Delete
        fun delete (entity: DatabaseEntity)

        @Query("DELETE FROM DatabaseEntity")
        fun deleteall()

        @Query("SELECT id1, dataType, ticketNumber FROM DatabaseEntity")
        fun getHistoryCursor(): Cursor

        @Query("SELECT id1, dataType, ticketNumber FROM DatabaseEntity WHERE id1 = :id1&dataType=:id2")
        fun getHistoryCursor(id1: Int,id2:Int): Cursor

        @Query("SELECT*FROM ResultEntity WHERE value=''")
        fun getAllUnfinishedResult():List<ResultEntity>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertDataToResult(entity: ResultEntity)

        @Query("SELECT*FROM ResultEntity")
        fun allFromResultDB():List<ResultEntity>





}
