package com.furkanbalci.travelguide.data.repositories

import com.furkanbalci.travelguide.data.models.article.ArticleResult
import com.furkanbalci.travelguide.data.models.attractions.AttractionResult
import com.furkanbalci.travelguide.data.models.country.CountryResult
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow

interface TriposoApiRepository {

    suspend fun getAttractions(city: String?): Flow<Resource<AttractionResult>>

    suspend fun findAttractions(vararg id: String): Flow<Resource<AttractionResult>>

    suspend fun getCountries(): Flow<Resource<CountryResult>>

    suspend fun getArticles(city: String): Flow<Resource<ArticleResult>>

}