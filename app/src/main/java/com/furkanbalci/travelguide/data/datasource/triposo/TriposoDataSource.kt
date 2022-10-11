package com.furkanbalci.travelguide.data.datasource.triposo

import com.furkanbalci.travelguide.data.models.article.ArticleResult
import com.furkanbalci.travelguide.data.models.attractions.AttractionResult
import com.furkanbalci.travelguide.data.models.country.CountryResult
import com.furkanbalci.travelguide.util.Resource
import kotlinx.coroutines.flow.Flow


interface TriposoDataSource {

    /**
     * Finds by id
     *
     * @param id Id
     * @return AttractionResult
     */
    fun findAttractions(id: String): Flow<Resource<AttractionResult>>

    /**
     * Get all attractions by country
     *
     * @param city City name
     * @return AttractionResult
     */
    fun getAttractions(city: String?): Flow<Resource<AttractionResult>>

    /**
     * Get all articles
     *
     * @return ArticleResult
     * @see ArticleResult
     */
    fun getArticles(city: String): Flow<Resource<ArticleResult>>

    /**
     * Get all countries
     *
     * @return CountryResult
     * @see CountryResult
     */
    fun getCountries(): Flow<Resource<CountryResult>>

}