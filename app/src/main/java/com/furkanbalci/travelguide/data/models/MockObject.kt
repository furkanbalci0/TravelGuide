package com.furkanbalci.travelguide.data.models


import com.furkanbalci.travelguide.di.DetailObject
import com.squareup.moshi.Json

data class MockObject(
    @Json(name = "category")
    val category: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "country")
    val country: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "images")
    val images: List<Image>,
    @Json(name = "isBookmark")
    val isBookmark: Boolean,
    @Json(name = "title")
    val title: String
) : DetailObject {
    data class Image(
        @Json(name = "altText")
        val altText: Any?,
        @Json(name = "height")
        val height: Int,
        @Json(name = "url")
        val url: String,
        @Json(name = "width")
        val width: Int
    )

    override fun getOtherImages(): List<String> {
        return images.map { it.url }
    }

    override fun mainImageUrl(): String {
        return images[0].url
    }

    override fun name(): String {
        return title
    }

    override fun description(): String {
        return description
    }

    override fun miniDescription(): String {
        return city
    }
}