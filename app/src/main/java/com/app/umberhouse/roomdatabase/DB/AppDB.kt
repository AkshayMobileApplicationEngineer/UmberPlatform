package com.app.umberhouse.roomdatabase.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.umberhouse.roomdatabase.Dao.CustomerDao
import com.app.umberhouse.roomdatabase.Entity.UserTableEntity

@Database(entities = [UserTableEntity::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun ContactDao(): CustomerDao


    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null

        fun getInstance(context: Context): AppDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "umber_house_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
