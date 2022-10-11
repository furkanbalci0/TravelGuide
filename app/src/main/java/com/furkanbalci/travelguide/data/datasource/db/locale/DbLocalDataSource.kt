package com.furkanbalci.travelguide.data.datasource.db.locale

import com.furkanbalci.travelguide.data.datasource.db.DbDataSource
import com.furkanbalci.travelguide.data.datasource.db.TravelDao
import com.furkanbalci.travelguide.data.models.Trip
import com.furkanbalci.travelguide.data.models.room.attraction.BookmarkEntity
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DbLocalDataSource @Inject constructor(private val travelDao: TravelDao) : DbDataSource {

    override suspend fun getBookmarks(): Flow<Resource<List<BookmarkEntity>>> = flow {
        emit(Resource.Loading(true))
        try {
            emit(Resource.Success(travelDao.getBookmarks()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    override suspend fun getTrips(): Flow<Resource<List<Trip>>> = flow {
        emit(Resource.Loading(true))
        try {
            emit(Resource.Success(travelDao.getTrips()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    override suspend fun insertAttraction(attraction: BookmarkEntity) {
        travelDao.insertAttraction(attraction)
    }

    override suspend fun insertTrip(trip: Trip) {
        travelDao.insertTrip(trip)
    }

    override suspend fun deleteAttraction(attraction: BookmarkEntity) {
        travelDao.deleteAttraction(attraction)
    }

    override suspend fun deleteAttractionById(attractionId: String) {
        travelDao.deleteAttractionById(attractionId)
    }

    override suspend fun deleteTripById(tripId: String) {
        travelDao.deleteTripById(tripId)
    }
}