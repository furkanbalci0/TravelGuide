package com.furkanbalci.travelguide.data.datasource.db

import com.furkanbalci.travelguide.data.models.Trip
import com.furkanbalci.travelguide.data.models.room.attraction.BookmarkEntity
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow

interface DbDataSource {

    suspend fun getBookmarks(): Flow<Resource<List<BookmarkEntity>>>

    suspend fun getTrips(): Flow<Resource<List<Trip>>>

    suspend fun insertAttraction(attraction: BookmarkEntity): Flow<Resource<Long>>

    suspend fun insertTrip(trip: Trip): Flow<Resource<Long>>

    suspend fun deleteAttractionById(attractionId: String): Flow<Resource<Int>>

    suspend fun deleteTripById(tripId: String): Flow<Resource<Int>>

}