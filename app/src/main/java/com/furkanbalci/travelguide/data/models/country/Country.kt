package com.furkanbalci.travelguide.data.models.country

import com.squareup.moshi.Json

data class Country(
    @Json(name = "attribution")
    val attribution: List<Attribution>,
    @Json(name = "coordinates")
    val coordinates: Coordinates,
    @Json(name = "country_id")
    val countryId: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "images")
    val images: List<Image>,
    @Json(name = "name")
    val name: String,
    @Json(name = "parent_id")
    val parentId: String,
    @Json(name = "score")
    val score: Double,
    @Json(name = "snippet")
    val snippet: String,
    @Json(name = "snippet_language_info")
    val snippetLanguageInfo: Any?,
    @Json(name = "type")
    val type: String
) {
    data class Attribution(
        @Json(name = "source_id")
        val sourceId: String,
        @Json(name = "url")
        val url: String
    )

    data class Coordinates(
        @Json(name = "latitude")
        val latitude: Double,
        @Json(name = "longitude")
        val longitude: Double
    )

    data class Image(
        @Json(name = "attribution")
        val attribution: Attribution,
        @Json(name = "caption")
        val caption: String,
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

}