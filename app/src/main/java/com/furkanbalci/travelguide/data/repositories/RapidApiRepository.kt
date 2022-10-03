package com.furkanbalci.travelguide.data.repositories

import com.furkanbalci.travelguide.data.datasource.rapid.remote.RapidDataSource
import com.furkanbalci.travelguide.data.models.attractions.AttractionResult
import com.furkanbalci.travelguide.data.models.country.CountryResult
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class RapidApiRepository {

    private var dataSource: RapidDataSource? = null

    init {
        dataSource = RapidDataSource()
    }

    fun getAttractions(city: String): Flow<Resource<AttractionResult>> {
        return dataSource!!.getAttractions(city)
    }

    fun getCountries(): Flow<Resource<CountryResult>> {
        return dataSource!!.getCountries()
    }
}