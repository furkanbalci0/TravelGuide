package com.furkanbalci.travelguide.data.datasource.mock

import com.furkanbalci.travelguide.data.models.MockObject
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow

interface MockDataSource {

    /**
     * Fetches a list of [MockObject] from the mock api.
     * @return a [Flow] of [Resource] which provides the data we want.
     * The first value emitted by the flow is [Resource.Loading].
     * The last value emitted by the flow is either [Resource.Success] or [Resource.Error].
     * In case of success, the [Resource.Success.data] contains the list of [MockObject].
     * In case of error, the [Resource.Error] contains the error message.
     */
    fun getResults(category: String?): Flow<Resource<List<MockObject>>>

}