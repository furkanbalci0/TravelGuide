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

    override suspend fun getBookmarks(): Flow<Resource<List<BookmarkEntity>>> {
        return execute { travelDao.getBookmarks() }
    }

    override suspend fun getTrips(): Flow<Resource<List<Trip>>> {
        return execute { travelDao.getTrips() }
    }

    override suspend fun insertAttraction(attraction: BookmarkEntity): Flow<Resource<Long>> {
        return execute { travelDao.insertAttraction(attraction) }
    }

    override suspend fun insertTrip(trip: Trip): Flow<Resource<Long>> {
        return execute { travelDao.insertTrip(trip) }
    }

    override suspend fun deleteAttractionById(attractionId: String): Flow<Resource<Int>> {
        return execute { travelDao.deleteAttractionById(attractionId) }
    }

    override suspend fun deleteTripById(tripId: String): Flow<Resource<Int>> {
        return execute { travelDao.deleteTripById(tripId) }
    }


    private fun <T> execute(block: () -> T) = flow {
        emit(Resource.Loading(true))
        try {
            emit(Resource.Success(block()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}