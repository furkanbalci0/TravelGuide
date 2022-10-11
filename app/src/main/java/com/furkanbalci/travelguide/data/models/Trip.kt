package com.furkanbalci.travelguide.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.furkanbalci.travelguide.di.DetailObject
import java.text.DateFormat
import java.util.*

@Entity(tableName = "trips")
data class Trip(

    @PrimaryKey
    @ColumnInfo(name = "trip_id")
    val id: String,

    @ColumnInfo(name = "trip_name")
    val tripName: String,

    @ColumnInfo(name = "starting_date")
    val startingDate: Long,

    @ColumnInfo(name = "ending_date")
    val endingDate: Long,

    @ColumnInfo(name = "main_image")
    val mainImage: String,
) : DetailObject {

    override fun getOtherImages(): List<String> {
        return listOf(mainImage)
    }

    override fun mainImageUrl(): String {
        return this.mainImage
    }

    override fun customName(): String {
        return this.tripName
    }

    override fun description(): String {
        //Date format.
        return "${DateFormat.getDateInstance().format(this.startingDate)} - ${
            DateFormat.getDateInstance().format(this.endingDate)
        }"
    }

    override fun miniDescription(): String {

        // Ending time - now time as a day.
        val diff = this.endingDate - Date().time
        val diffDays = diff / (24 * 60 * 60 * 1000)

        return "$diffDays days left"
    }

    override fun getCustomId(): String {
        return this.id
    }
}