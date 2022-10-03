package com.furkanbalci.travelguide.data.datasource.rapid

import com.furkanbalci.travelguide.data.models.attractions.AttractionResult
import com.furkanbalci.travelguide.data.models.country.CountryResult
import com.furkanbalci.travelguide.util.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RapidApiService {


    @GET("location.json")
    suspend fun getCountries(): Response<CountryResult>

    @GET("poi.json?tag_labels=sightseeing&bookable=1&fields=properties,booking_info,name,images,intro&order_by=-score")
    suspend fun getAttractions(@Query("location_id") city: String): Response<AttractionResult>

    companion object {

        fun create(): RapidApiService {
            val moshi: Moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            //Add headers to the request
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("X-Triposo-Account", "WFPSQBL0")
                        .addHeader("X-Triposo-Token", "yijckvulxx30rztq5ruqp6gnts8f5vup")
                        .build()
                    chain.proceed(request)
                }
                .build()


            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.RAPID_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()

            return retrofit.create(RapidApiService::class.java)
        }
    }

}