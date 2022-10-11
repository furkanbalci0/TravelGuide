package com.furkanbalci.travelguide.data.datasource.mock.remote

import com.furkanbalci.travelguide.data.datasource.mock.MockApiService
import com.furkanbalci.travelguide.data.datasource.mock.MockDataSource
import com.furkanbalci.travelguide.data.models.MockObject
import com.furkanbalci.travelguide.di.NetworkModule
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MockRetrofitDataSource @Inject constructor(
    @NetworkModule.MockApi private val service: MockApiService
) : MockDataSource {

    /**
     * @inheritDoc
     */
    override fun getResults(category: String?): Flow<Resource<List<MockObject>>> = flow {
        try {
            emit(Resource.Loading(true))
            val response = service.getMockResult(category)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
            e.printStackTrace()
        }
    }
}