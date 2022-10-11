package com.furkanbalci.travelguide.data.repositories

import com.furkanbalci.travelguide.data.models.Trip
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.data.models.room.attraction.BookmarkEntity
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow

interface DbRepository {

    suspend fun getBookmarks(): Flow<Resource<List<BookmarkEntity>>>

    suspend fun getTrips(): Flow<Resource<List<Trip>>>

    suspend fun insert(attraction: Attraction)

    suspend fun insert(trip: Trip)

    suspend fun delete(attractionId: String)

    suspend fun deleteTrip(tripId: String)

}