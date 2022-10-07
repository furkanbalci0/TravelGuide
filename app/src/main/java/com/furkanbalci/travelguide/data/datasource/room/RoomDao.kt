package com.furkanbalci.travelguide.data.datasource.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.furkanbalci.travelguide.data.models.room.attraction.AttractionEntity

@Dao
interface RoomDao {

    /*
     * Attractions
     */
    @Query("SELECT * FROM bookmark")
    suspend fun getBookmarks(): List<AttractionEntity>

    @Insert
    suspend fun insertAttraction(attraction: AttractionEntity)

    @Delete
    suspend fun deleteAttraction(attraction: AttractionEntity)

    @Query("DELETE FROM bookmark WHERE attraction_id = :attractionId")
    suspend fun deleteAttractionById(attractionId: String)


}