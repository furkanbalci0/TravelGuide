package com.furkanbalci.travelguide.data.models.attractions


import com.squareup.moshi.Json

data class AttractionResult(
    @Json(name = "is_deprecated")
    val isDeprecated: String,
    @Json(name = "more")
    val more: Boolean,
    @Json(name = "results")
    val results: List<Attraction>
)