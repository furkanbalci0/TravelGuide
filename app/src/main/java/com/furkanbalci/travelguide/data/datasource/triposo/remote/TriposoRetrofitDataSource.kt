package com.furkanbalci.travelguide.data.datasource.triposo.remote

import com.furkanbalci.travelguide.data.datasource.triposo.TriposoApiService
import com.furkanbalci.travelguide.data.datasource.triposo.TriposoDataSource
import com.furkanbalci.travelguide.data.models.article.ArticleResult
import com.furkanbalci.travelguide.data.models.attractions.AttractionResult
import com.furkanbalci.travelguide.data.models.country.CountryResult
import com.furkanbalci.travelguide.di.NetworkModule
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TriposoRetrofitDataSource @Inject constructor(
    @NetworkModule.TriposoApi
    private val service: TriposoApiService
) : TriposoDataSource {

    /**
     * Get all countries
     *
     * @return CountryResult
     * @see CountryResult
     */
    override fun getCountries(): Flow<Resource<CountryResult>> = flow {

        try {
            emit(Resource.Loading(true))

            val response = service.getCountries()

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

    /**
     * Get all articles
     *
     * @return ArticleResult
     * @see ArticleResult
     */
    override fun getArticles(city: String): Flow<Resource<ArticleResult>> = flow {

        try {
            emit(Resource.Loading(true))

            val response = service.getArticles(city)

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

    /**
     * Get all attractions by country
     *
     * @param city City name
     * @return AttractionResult
     */
    override fun getAttractions(city: String?): Flow<Resource<AttractionResult>> = flow {

        try {
            emit(Resource.Loading(true))

            val response = service.getAttractions(city)

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

    /**
     * Finds by id
     *
     * @param id Id
     * @return AttractionResult
     */
    override fun findAttractions(id: String): Flow<Resource<AttractionResult>> = flow {

        try {
            emit(Resource.Loading(true))

            val response = service.findAttractions(id)

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