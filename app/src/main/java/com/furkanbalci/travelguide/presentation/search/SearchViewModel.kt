package com.furkanbalci.travelguide.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.data.repositories.DbRepository
import com.furkanbalci.travelguide.data.repositories.TriposoApiRepository
import com.furkanbalci.travelguide.presentation.base.BaseViewModel
import com.furkanbalci.travelguide.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val triposoRepository: TriposoApiRepository,
    private val dbRepository: DbRepository
) : BaseViewModel() {

    val attractionsLiveData = MutableLiveData<List<Attraction>>()
    val bookmarkLiveData = MutableLiveData<List<Attraction>>()

    private var bookmarkList: MutableList<String> = mutableListOf()
    var lastSelectedCity = "Amsterdam"

    init {
        getBookmarks()
    }

    /**
     * Initialize room database.
     */
    private fun getBookmarks() {
        viewModelScope.launch(Dispatchers.Main) {

            dbRepository.getBookmarks().collect { it ->

                when (it) {

                    is Resource.Loading -> {
                        loading.postValue(true)
                        error.postValue(false)
                    }

                    is Resource.Error -> {
                        loading.postValue(false)
                        error.postValue(true)
                    }

                    is Resource.Success -> {
                        loading.postValue(false)
                        error.postValue(false)
                        it.data?.let { list ->
                            bookmarkList = list.map { it.attractionId }.toMutableList()
                        }
                        getAttractions()
                        findAttractions()
                    }
                }
            }
        }
    }

    /**
     * Load data from repository.
     * @param city City name.
     */
    fun getAttractions(city: String = "Amsterdam") {
        lastSelectedCity = city

        viewModelScope.launch(Dispatchers.Main) {

            //Get attractions.
            triposoRepository.getAttractions(city).collect() {
                when (it) {

                    is Resource.Loading -> {
                        loading.postValue(true)
                        error.postValue(false)
                    }

                    is Resource.Error -> {
                        loading.postValue(false)
                        error.postValue(true)
                    }

                    is Resource.Success -> {

                        loading.postValue(false)
                        error.postValue(false)
                        //Get attractions.
                        val attractions = it.data!!.results.filter { attraction -> attraction.getOtherImages().isNotEmpty() }

                        //If bookmarkList contains attraction id, set bookmarked true.
                        attractions.asSequence()
                            .filter { item -> bookmarkList.contains(item.id) }
                            .forEach { item -> item.isBookmarked = true }

                        //Post filtered list.
                        attractionsLiveData.postValue(attractions)
                    }
                }
            }
        }
    }

    fun findAttractions() {

        viewModelScope.launch(Dispatchers.Main) {

            //Get attractions.
            triposoRepository.findAttractions(*bookmarkList.toTypedArray()).collect() {

                when (it) {

                    is Resource.Loading -> {
                        loading.postValue(true)
                        error.postValue(false)
                    }

                    is Resource.Error -> {
                        loading.postValue(false)
                        error.postValue(true)
                    }

                    is Resource.Success -> {
                        loading.postValue(false)
                        error.postValue(false)
                        //Get attractions.
                        val attractions = it.data!!.results.filter { attraction -> attraction.getOtherImages().isNotEmpty() }

                        //If bookmarkList contains attraction id, set bookmarked true.
                        attractions.asSequence()
                            .filter { item -> bookmarkList.contains(item.id) }
                            .forEach { item -> item.isBookmarked = true }

                        //Post filtered list.
                        bookmarkLiveData.postValue(attractions)
                    }
                }
            }
        }
    }

    fun addBookmark(
        attraction: Attraction,
        onBookmarkAddButtonClick: (Attraction) -> Unit
    ) {
        viewModelScope.launch {
            dbRepository.insert(attraction).collect() {
                when (it) {

                    is Resource.Loading -> {
                        loading.postValue(true)
                        error.postValue(false)
                    }

                    is Resource.Error -> {
                        loading.postValue(false)
                        error.postValue(true)
                    }

                    is Resource.Success -> {
                        loading.postValue(false)
                        error.postValue(false)
                        bookmarkList.add(attraction.id)
                        onBookmarkAddButtonClick(attraction)
                    }
                }
            }
        }
    }

    fun removeBookmark(
        attractionId: String,
        onBookmarkRemoveButtonClick: (String) -> Unit
    ) {
        viewModelScope.launch {
            dbRepository.delete(attractionId).collect() {
                when (it) {

                    is Resource.Loading -> {
                        loading.postValue(true)
                        error.postValue(false)
                    }

                    is Resource.Error -> {
                        loading.postValue(false)
                        error.postValue(true)
                    }

                    is Resource.Success -> {
                        loading.postValue(false)
                        error.postValue(false)
                        bookmarkList.remove(attractionId)
                        onBookmarkRemoveButtonClick(attractionId)
                    }
                }
            }
        }
    }
}

