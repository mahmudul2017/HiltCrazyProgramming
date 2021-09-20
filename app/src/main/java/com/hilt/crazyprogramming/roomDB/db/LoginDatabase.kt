package com.hilt.crazyprogramming.roomDB.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hilt.crazyprogramming.roomDB.dao.LoginUserDao
import com.hilt.crazyprogramming.roomDB.model.LoginUser

@Database(entities = [LoginUser::class], version = 1, exportSchema = false)
abstract class LoginDatabase: RoomDatabase() {
    abstract fun loginUserDao(): LoginUserDao

    companion object {
        @Volatile
        private var INSTANCE: LoginDatabase? = null

        fun getDataBaseClient(context: Context): LoginDatabase {
            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, LoginDatabase::class.java, "HILT_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!
            }
        }
    }
}