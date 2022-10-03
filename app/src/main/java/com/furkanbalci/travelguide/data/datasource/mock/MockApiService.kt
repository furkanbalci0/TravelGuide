package com.furkanbalci.travelguide.data.datasource.mock

import com.furkanbalci.travelguide.data.models.MockObject
import com.furkanbalci.travelguide.util.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MockApiService {

    /**
     * Returns a list of [MockObject]s from the mock api.
     * @param category The category of the mock object.
     */
    @GET("AllTravelList")
    suspend fun getMockResult(@Query("category") category: String?): Response<List<MockObject>>

    companion object {

        fun create(): MockApiService {
            val moshi: Moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.MOCK_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

            return retrofit.create(MockApiService::class.java)
        }
    }

}