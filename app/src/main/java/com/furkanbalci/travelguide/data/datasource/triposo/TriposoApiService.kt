package com.furkanbalci.travelguide.data.datasource.triposo

import com.furkanbalci.travelguide.data.models.article.ArticleResult
import com.furkanbalci.travelguide.data.models.attractions.AttractionResult
import com.furkanbalci.travelguide.data.models.country.CountryResult
import com.furkanbalci.travelguide.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TriposoApiService {

    @GET("location.json")
    suspend fun getCountries(
        @Query("count") count: Int = Constants.API_ITEM_LIMIT
    ): Response<CountryResult>

    @GET("poi.json")
    suspend fun getAttractions(
        @Query("location_id") city: String?,
        @Query("tag_labels") label: String = "sightseeing",
        @Query("bookable") bookable: Int = 1,
        @Query("fields") fields: String = "id,properties,booking_info,name,images,intro",
        @Query("order_by") order_by: String = "-score",
        @Query("count") count: Int = Constants.API_ITEM_LIMIT
    ): Response<AttractionResult>

    @GET("poi.json")
    suspend fun findAttractions(
        @Query("id") attractionId: String,
        @Query("tag_labels") label: String = "sightseeing",
        @Query("bookable") bookable: Int = 1,
        @Query("fields") fields: String = "id,properties,booking_info,name,images,intro",
        @Query("order_by") order_by: String = "-score",
        @Query("count") count: Int = Constants.API_ITEM_LIMIT
    ): Response<AttractionResult>

    @GET("article.json")
    suspend fun getArticles(
        @Query("location_ids") city: String? = null,
        @Query("order_by") order_by: String = "-score",
        @Query("count") count: Int = Constants.API_ITEM_LIMIT
    ): Response<ArticleResult>

}