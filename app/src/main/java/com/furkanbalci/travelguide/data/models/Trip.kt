package com.furkanbalci.travelguide.data.models

import com.furkanbalci.travelguide.di.DetailObject
import java.text.DateFormat
import java.util.*

data class Trip(
    val name: String,
    val startingDate: Date,
    val endingDate: Date,
    val images: List<String>,
    val mainImage: String,
) : DetailObject {

    override fun getOtherImages(): List<String> {
        return this.images
    }

    override fun mainImageUrl(): String {
        return this.mainImage
    }

    override fun name(): String {
        return this.name
    }

    override fun description(): String {
        //Date format.
        //TODO BURAYI DEĞİŞTİR.
        return "${DateFormat.getDateInstance().format(this.startingDate)} - ${
            DateFormat.getDateInstance().format(this.endingDate)
        }"
    }

    override fun miniDescription(): String {
        return this.endingDate.toString()
    }
}