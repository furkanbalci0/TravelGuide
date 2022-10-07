package com.furkanbalci.travelguide.data.models.article


import com.squareup.moshi.Json

data class ArticleResult(
    @Json(name = "is_deprecated")
    val isDeprecated: String,
    @Json(name = "more")
    val more: Boolean,
    @Json(name = "results")
    val results: List<Article>
) {

}