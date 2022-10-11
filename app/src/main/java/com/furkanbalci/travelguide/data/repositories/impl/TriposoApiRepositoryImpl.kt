package com.furkanbalci.travelguide.data.repositories.impl

import com.furkanbalci.travelguide.data.datasource.triposo.TriposoDataSource
import com.furkanbalci.travelguide.data.models.article.ArticleResult
import com.furkanbalci.travelguide.data.models.attractions.AttractionResult
import com.furkanbalci.travelguide.data.models.country.CountryResult
import com.furkanbalci.travelguide.data.repositories.TriposoApiRepository
import com.furkanbalci.travelguide.di.NetworkModule
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TriposoApiRepositoryImpl @Inject constructor(
    @NetworkModule.TriposoApi private val dataSource: TriposoDataSource
) : TriposoApiRepository {

    override suspend fun getAttractions(city: String?): Flow<Resource<AttractionResult>> {
        return dataSource.getAttractions(city)
    }

    override suspend fun findAttractions(vararg id: String): Flow<Resource<AttractionResult>> {
        return dataSource.findAttractions(id.joinToString("|"))
    }

    override suspend fun getCountries(): Flow<Resource<CountryResult>> {
        return dataSource.getCountries()
    }

    override suspend fun getArticles(city: String): Flow<Resource<ArticleResult>> {
        return dataSource.getArticles(city)
    }
}