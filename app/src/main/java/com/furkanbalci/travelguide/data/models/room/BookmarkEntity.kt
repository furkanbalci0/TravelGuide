package com.furkanbalci.travelguide.data.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class BookmarkEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val attractionId: String,

    @ColumnInfo(name = "date")
    val date: Long,
)