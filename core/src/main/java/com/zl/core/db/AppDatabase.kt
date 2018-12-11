package com.zl.core.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.zl.core.BaseApplication

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/2/5 19:34.<br/>
 */
//@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

//    abstract fun userDao(): UserDao
//
//    companion object {
//
//        @Volatile private var INSTANCE: AppDatabase? = null
//
//        fun getInstance(): AppDatabase =
//                INSTANCE ?: synchronized(this) {
//                    INSTANCE ?: buildDatabase().also { INSTANCE = it }
//                }
//
//        private fun buildDatabase() =
//                Room.databaseBuilder(BaseApplication.instance,
//                        AppDatabase::class.java, "chat.db")
//                        .build()
//    }

}