package com.furkanbalci.travelguide.data.repositories

import com.furkanbalci.travelguide.data.models.MockObject
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow

interface MockApiRepository {

    suspend fun getResults(category: String?): Flow<Resource<List<MockObject>>>

}