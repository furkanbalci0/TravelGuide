package com.furkanbalci.travelguide.data.models.room.attraction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class BookmarkEntity(

    @PrimaryKey
    @ColumnInfo(name = "attraction_id")
    val attractionId: String,
)