package com.furkanbalci.travelguide.data.repositories.impl

import com.furkanbalci.travelguide.data.datasource.mock.MockDataSource
import com.furkanbalci.travelguide.data.models.MockObject
import com.furkanbalci.travelguide.data.repositories.MockApiRepository
import com.furkanbalci.travelguide.di.NetworkModule
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MockApiRepositoryImpl @Inject constructor(
    @NetworkModule.MockApi private val dataSource: MockDataSource
) : MockApiRepository {

    override suspend fun getResults(category: String?): Flow<Resource<List<MockObject>>> {
        return dataSource.getResults(category)
    }
}