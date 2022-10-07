package com.furkanbalci.travelguide.data.repositories

import com.furkanbalci.travelguide.data.datasource.triposo.remote.TriposoDataSource
import com.furkanbalci.travelguide.data.models.article.ArticleResult
import com.furkanbalci.travelguide.data.models.attractions.AttractionResult
import com.furkanbalci.travelguide.data.models.country.CountryResult
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class TriposoApiRepository {

    private var dataSource: TriposoDataSource? = null

    init {
        dataSource = TriposoDataSource()
    }

    fun getAttractions(city: String): Flow<Resource<AttractionResult>> {
        return dataSource!!.getAttractions(city)
    }

    fun findAttraction(id: String): Flow<Resource<AttractionResult>> {
        return dataSource!!.findAttraction(id)
    }

    fun getCountries(): Flow<Resource<CountryResult>> {
        return dataSource!!.getCountries()
    }

    fun getArticles(): Flow<Resource<ArticleResult>> {
        return dataSource!!.getArticles()
    }
}