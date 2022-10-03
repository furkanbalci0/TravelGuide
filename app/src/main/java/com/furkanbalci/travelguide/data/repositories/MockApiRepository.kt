package com.furkanbalci.travelguide.data.repositories

import com.furkanbalci.travelguide.data.datasource.mock.remote.MockDataSource
import com.furkanbalci.travelguide.data.models.MockObject
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class MockApiRepository {

    private var dataSource: MockDataSource? = null

    init {
        dataSource = MockDataSource()
    }

    fun getHotels(): Flow<Resource<List<MockObject>>> {
        return dataSource!!.getResults("hotel")
    }

    fun getFlights(): Flow<Resource<List<MockObject>>> {
        return dataSource!!.getResults("flight")
    }

    fun getTransportations(): Flow<Resource<List<MockObject>>> {
        return dataSource!!.getResults("transportation")
    }

    fun getMightNeed(): Flow<Resource<List<MockObject>>> {
        return dataSource!!.getResults("mightneed")
    }

    fun getTopPick(): Flow<Resource<List<MockObject>>> {
        return dataSource!!.getResults("toppick")
    }

    fun getResults(category: String?): Flow<Resource<List<MockObject>>> {
        return dataSource!!.getResults(category)
    }
}