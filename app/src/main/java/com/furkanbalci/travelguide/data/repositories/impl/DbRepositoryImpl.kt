package com.furkanbalci.travelguide.data.repositories.impl

import com.furkanbalci.travelguide.data.datasource.db.DbDataSource
import com.furkanbalci.travelguide.data.models.Trip
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.data.models.room.attraction.BookmarkEntity
import com.furkanbalci.travelguide.data.repositories.DbRepository
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbRepositoryImpl @Inject constructor(private val dataSource: DbDataSource) : DbRepository {

    override suspend fun getBookmarks(): Flow<Resource<List<BookmarkEntity>>> {
        return dataSource.getBookmarks()
    }

    override suspend fun getTrips(): Flow<Resource<List<Trip>>> {
        return dataSource.getTrips()
    }

    override suspend fun insert(attraction: Attraction): Flow<Resource<Long>> {
        return dataSource.insertAttraction(BookmarkEntity(attraction.id))
    }

    override suspend fun insert(trip: Trip): Flow<Resource<Long>> {
        return dataSource.insertTrip(trip)
    }

    override suspend fun delete(attractionId: String): Flow<Resource<Int>> {
        return dataSource.deleteAttractionById(attractionId)
    }

    override suspend fun deleteTrip(tripId: String): Flow<Resource<Int>> {
        return dataSource.deleteTripById(tripId)
    }

}