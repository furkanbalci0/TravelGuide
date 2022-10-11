package com.furkanbalci.travelguide.data.datasource.mock

import com.furkanbalci.travelguide.data.models.MockObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MockApiService {

    /**
     * Returns a list of [MockObject]s from the mock api.
     * @param category The category of the mock object.
     */
    @GET("AllTravelList")
    suspend fun getMockResult(@Query("category") category: String?): Response<List<MockObject>>

}