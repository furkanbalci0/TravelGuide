package com.furkanbalci.travelguide.data.datasource.triposo

import com.furkanbalci.travelguide.data.models.article.ArticleResult
import com.furkanbalci.travelguide.data.models.attractions.AttractionResult
import com.furkanbalci.travelguide.data.models.country.CountryResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TriposoApiService {

    @GET("location.json?count=100")
    suspend fun getCountries(): Response<CountryResult>

    @GET("poi.json?tag_labels=sightseeing&bookable=1&fields=id,properties,booking_info,name,images,intro&order_by=-score&count=100")
    suspend fun getAttractions(@Query("location_id") city: String?): Response<AttractionResult>

    @GET("poi.json?tag_labels=sightseeing&bookable=1&fields=id,properties,booking_info,name,images,intro&order_by=-score&count=100")
    suspend fun findAttractions(@Query("id") attractionId: String): Response<AttractionResult>

    @GET("article.json?order_by=-score&count=100")
    suspend fun getArticles(@Query("location_ids") city: String? = null): Response<ArticleResult>

}