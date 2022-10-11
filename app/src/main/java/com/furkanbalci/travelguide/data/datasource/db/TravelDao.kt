package com.furkanbalci.travelguide.data.datasource.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.furkanbalci.travelguide.data.models.Trip
import com.furkanbalci.travelguide.data.models.room.attraction.BookmarkEntity

@Dao
interface TravelDao {

    /*
     * Attractions
     */
    @Query("SELECT * FROM bookmark")
    fun getBookmarks(): List<BookmarkEntity>

    //Trips
    @Query("SELECT * FROM trips")
    fun getTrips(): List<Trip>

    @Insert
    fun insertAttraction(attraction: BookmarkEntity)

    @Insert
    fun insertTrip(trip: Trip)

    @Delete
    fun deleteAttraction(attraction: BookmarkEntity) //TODO: BURAYA BAK VE SİL

    @Query("DELETE FROM bookmark WHERE attraction_id = :attractionId")
    fun deleteAttractionById(attractionId: String)

    @Query("DELETE FROM trips WHERE trip_id = :tripId")
    fun deleteTripById(tripId: String)


}