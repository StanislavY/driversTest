package com.example.order.core

import android.app.Application
import androidx.room.Room
import com.example.order.datasource.Room.DataBaseFromFB.Database
import com.example.order.datasource.Room.DataBaseFromFB.DatabaseDAO

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this

    }

    companion object {
        private var appInstance: App? = null
        private var db1C: Database? = null
        private val DB1C_NAME = GlobalConstAndVars.DATABASE_NAME

        fun get1CDAO(): DatabaseDAO {
            if (db1C == null) {
                synchronized(Database::class.java) {
                    if (db1C == null) {
                        if (appInstance == null) {
                            throw IllegalStateException("Application ids null meanwhile creating database")

                        }
                       /* val MIGRATION_1_2: Migration = object : Migration(1, 2) {//шаблон кода на случай миграции
                            override fun migrate(database: SupportSQLiteDatabase) {
                                database.execSQL("CREATE TABLE `ResultEntity` ( `id1` TEXT NOT NULL,`id2` TEXT NOT NULL,`name` TEXT NOT NULL,`value` TEXT NOT NULL,PRIMARY KEY   (`id1`,`id2`,`name`) ) ")


                            }
                        }*/

                        db1C = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            Database::class.java, DB1C_NAME
                        )
                            .allowMainThreadQueries()
                            /*.addMigrations(MIGRATION_1_2)*///шаблон кода на случай миграции
                            .build()


                    }
                }
            }
            return db1C!!.databaseFrom1CDAO()

        }

    }
}