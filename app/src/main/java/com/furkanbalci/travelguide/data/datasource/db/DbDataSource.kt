package com.furkanbalci.travelguide.data.datasource.db

import com.furkanbalci.travelguide.data.models.Trip
import com.furkanbalci.travelguide.data.models.room.attraction.BookmarkEntity
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow

interface DbDataSource {

    suspend fun getBookmarks(): Flow<Resource<List<BookmarkEntity>>>

    suspend fun getTrips(): Flow<Resource<List<Trip>>>

    suspend fun insertAttraction(attraction: BookmarkEntity)

    suspend fun insertTrip(trip: Trip)

    suspend fun deleteAttraction(attraction: BookmarkEntity)

    suspend fun deleteAttractionById(attractionId: String)

    suspend fun deleteTripById(tripId: String)

}