package com.furkanbalci.travelguide.data.datasource.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.furkanbalci.travelguide.data.models.Trip
import com.furkanbalci.travelguide.data.models.room.attraction.BookmarkEntity

@Database(entities = [BookmarkEntity::class, Trip::class], version = 9, exportSchema = false)
abstract class TravelDatabase : RoomDatabase() {

    abstract fun travelDao(): TravelDao

}