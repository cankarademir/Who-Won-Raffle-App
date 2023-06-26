package com.cankarademir.kimkazandiapplication.configs

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cankarademir.kimkazandiapplication.dao.CekilisDao
import com.cankarademir.kimkazandiapplication.models.Data


@Database(entities = [Data::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun CekilisDao(): CekilisDao
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}