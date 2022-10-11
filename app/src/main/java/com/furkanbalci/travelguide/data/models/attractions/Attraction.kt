package com.furkanbalci.travelguide.data.models.attractions


import com.furkanbalci.travelguide.di.DetailObject
import com.squareup.moshi.Json

data class Attraction(
    @Json(name = "id")
    val id: String,
    @Json(name = "booking_info")
    val bookingInfo: BookingInfo,
    @Json(name = "images")
    val images: List<Image>,
    @Json(name = "intro")
    val intro: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "properties")
    val properties: List<Property?>,
    @Json(name = "sightseeing_score")
    val sightseeingScore: Double,
    var isBookmarked: Boolean = false
) : DetailObject {
    data class BookingInfo(
        @Json(name = "price")
        val price: Price,
        @Json(name = "vendor")
        val vendor: String,
        @Json(name = "vendor_object_id")
        val vendorObjectId: String,
        @Json(name = "vendor_object_url")
        val vendorObjectUrl: String
    ) {
        data class Price(
            @Json(name = "amount")
            val amount: String,
            @Json(name = "currency")
            val currency: String
        )
    }

    data class Image(
        @Json(name = "attribution")
        val attribution: Attribution,
        @Json(name = "caption")
        val caption: String?,
        @Json(name = "sizes")
        val sizes: Sizes,
        @Json(name = "source_id")
        val sourceId: String,
        @Json(name = "source_url")
        val sourceUrl: String
    ) {
        data class Attribution(
            @Json(name = "attribution_link")
            val attributionLink: String,
            @Json(name = "attribution_text")
            val attributionText: String,
            @Json(name = "format")
            val format: String,
            @Json(name = "license_link")
            val licenseLink: String?,
            @Json(name = "license_text")
            val licenseText: String
        )

        data class Sizes(
            @Json(name = "medium")
            val medium: Medium,
            @Json(name = "original")
            val original: Original,
            @Json(name = "thumbnail")
            val thumbnail: Thumbnail
        ) {
            data class Medium(
                @Json(name = "bytes")
                val bytes: Int,
                @Json(name = "format")
                val format: String,
                @Json(name = "height")
                val height: Int,
                @Json(name = "url")
                val url: String,
                @Json(name = "width")
                val width: Int
            )

            data class Original(
                @Json(name = "bytes")
                val bytes: Int,
                @Json(name = "format")
                val format: String,
                @Json(name = "height")
                val height: Int,
                @Json(name = "url")
                val url: String,
                @Json(name = "width")
                val width: Int
            )

            data class Thumbnail(
                @Json(name = "bytes")
                val bytes: Int,
                @Json(name = "format")
                val format: String,
                @Json(name = "height")
                val height: Int,
                @Json(name = "url")
                val url: String,
                @Json(name = "width")
                val width: Int
            )
        }
    }

    data class Property(
        @Json(name = "key")
        val key: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "ordinal")
        val ordinal: Int?,
        @Json(name = "value")
        val value: String?
    )

    override fun mainImageUrl(): String {
        return images.firstOrNull()?.sizes?.medium?.url ?: ""
    }

    override fun customName(): String {
        return name
    }

    override fun description(): String {
        return intro
    }

    override fun miniDescription(): String {
        if (properties.size > 2) {
            return properties[2]?.value ?: name
        }
        return name
    }

    override fun getCustomId(): String {
        return id
    }

    override fun getOtherImages(): List<String> {
        return images.map { it.sizes.medium.url }
    }
}