package com.furkanbalci.travelguide.data.repositories

import android.content.Context
import androidx.annotation.WorkerThread
import com.furkanbalci.travelguide.data.datasource.room.RoomDao
import com.furkanbalci.travelguide.data.datasource.room.local.LocalRoomDatabase
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.data.models.room.attraction.AttractionEntity
import javax.inject.Singleton

@Singleton
class RoomRepository(context: Context) {

    private var roomDao: RoomDao

    init {
        roomDao = LocalRoomDatabase.getDatabase(context).roomDao()
    }

    suspend fun getAllAttractions(): List<AttractionEntity> {
        return roomDao.getAllAttractions()
    }

    @WorkerThread
    suspend fun insert(attraction: Attraction) {
        roomDao.insertAttraction(AttractionEntity(attraction.id))
    }

    @WorkerThread
    suspend fun delete(attraction: Attraction) {
        roomDao.deleteAttraction(AttractionEntity(attraction.id))
    }
}