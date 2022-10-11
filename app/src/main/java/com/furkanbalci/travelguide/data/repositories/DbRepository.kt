package com.furkanbalci.travelguide.data.repositories

import com.furkanbalci.travelguide.data.models.Trip
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.data.models.room.attraction.BookmarkEntity
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow

interface DbRepository {

    suspend fun getBookmarks(): Flow<Resource<List<BookmarkEntity>>>

    suspend fun getTrips(): Flow<Resource<List<Trip>>>

    suspend fun insert(attraction: Attraction): Flow<Resource<Long>>

    suspend fun insert(trip: Trip): Flow<Resource<Long>>

    suspend fun delete(attractionId: String): Flow<Resource<Int>>

    suspend fun deleteTrip(tripId: String): Flow<Resource<Int>>

}