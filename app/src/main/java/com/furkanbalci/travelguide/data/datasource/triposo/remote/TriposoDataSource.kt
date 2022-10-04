package com.furkanbalci.travelguide.data.datasource.triposo.remote

import com.furkanbalci.travelguide.data.datasource.triposo.TriposoApiService
import com.furkanbalci.travelguide.data.models.attractions.AttractionResult
import com.furkanbalci.travelguide.data.models.country.CountryResult
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TriposoDataSource {

    private var service: TriposoApiService = TriposoApiService.create()

    /**
     * Get all countries
     *
     * @return CountryResult
     * @see CountryResult
     */
    fun getCountries(): Flow<Resource<CountryResult>> = flow {

        try {
            emit(Resource.Loading())

            val response = service.getCountries()

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                }
            }

        } catch (e: Exception) {
            emit(Resource.Error(e))
            e.printStackTrace()
        }

    }

    /**
     * Get all attractions by country
     *
     * @param city City name
     * @return AttractionResult
     */
    fun getAttractions(city: String): Flow<Resource<AttractionResult>> = flow {

        try {
            emit(Resource.Loading())

            val response = service.getAttractions(city)

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                }
            }

        } catch (e: Exception) {
            emit(Resource.Error(e))
            e.printStackTrace()
        }
    }

}