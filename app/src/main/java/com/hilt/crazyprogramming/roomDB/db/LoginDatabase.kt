package com.hilt.crazyprogramming.roomDB.db

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hilt.crazyprogramming.roomDB.bitmap.BitmapConverter
import com.hilt.crazyprogramming.roomDB.dao.LoginUserDao
import com.hilt.crazyprogramming.roomDB.model.LoginUser

@Database(entities = [LoginUser::class], version = 1, exportSchema = false)
abstract class LoginDatabase: RoomDatabase() {
    abstract fun loginUserDao(): LoginUserDao

    companion object {
        @Volatile
        private var INSTANCE: LoginDatabase? = null

        private val migration_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE HiltLogIn ADD COLUMN userpic Bitmap DEFAULT ''")
            }
        }

        fun getDataBaseClient(context: Context): LoginDatabase {
            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, LoginDatabase::class.java, "HILT_DB")
                    .fallbackToDestructiveMigration()
                    //.addMigrations(migration_2_3)
                    .allowMainThreadQueries()
                    .build()

                return INSTANCE!!
            }
        }
    }
}