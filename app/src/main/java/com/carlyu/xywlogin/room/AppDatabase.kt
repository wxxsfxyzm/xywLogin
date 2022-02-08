package com.carlyu.xywlogin.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.carlyu.xywlogin.common.Constant.LATEST_VERSION


@Database(
    version = LATEST_VERSION, entities = [User::class],
    // autoMigrations property for future maintenance
    // autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user.db" //数据库名称
                ).allowMainThreadQueries().build()
            }
            return instance as AppDatabase
        }
    }
}
