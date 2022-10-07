package com.furkanbalci.travelguide.data.datasource.room.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.furkanbalci.travelguide.data.datasource.room.RoomDao
import com.furkanbalci.travelguide.data.models.room.attraction.AttractionEntity

@Database(entities = [AttractionEntity::class], version = 3, exportSchema = false)
abstract class LocalRoomDatabase : RoomDatabase() {

    abstract fun roomDao(): RoomDao

    companion object {
        @Volatile
        private var INSTANCE: LocalRoomDatabase? = null

        fun getDatabase(context: Context): LocalRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalRoomDatabase::class.java, "travel_app_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}