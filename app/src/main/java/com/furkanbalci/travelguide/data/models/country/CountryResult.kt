package com.furkanbalci.travelguide.data.models.country


import com.squareup.moshi.Json

data class CountryResult(
    @Json(name = "is_deprecated")
    val isDeprecated: String,
    @Json(name = "more")
    val more: Boolean,
    @Json(name = "results")
    val results: List<Country>
)