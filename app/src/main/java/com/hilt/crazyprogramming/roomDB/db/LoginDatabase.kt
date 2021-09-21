package com.hilt.crazyprogramming.roomDB.db

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hilt.crazyprogramming.roomDB.bitmap.BitmapConverter
import com.hilt.crazyprogramming.roomDB.dao.LoginUserDao
import com.hilt.crazyprogramming.roomDB.model.LoginUser

@Database(entities = [LoginUser::class], version = 3, exportSchema = false)
@TypeConverters(BitmapConverter::class)
abstract class LoginDatabase: RoomDatabase() {
    abstract fun loginUserDao(): LoginUserDao

    companion object {
        @Volatile
        private var INSTANCE: LoginDatabase? = null

        val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE HiltLogIn ADD COLUMN userpic TEXT DEFAULT ''")
            }
        }

        fun getDataBaseClient(context: Context): LoginDatabase {
            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, LoginDatabase::class.java, "HILT_DATABASE")
                    //.fallbackToDestructiveMigration()
                    .addMigrations(migration_1_2)
                    .build()

                return INSTANCE!!
            }
        }
    }
}