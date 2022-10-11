package com.furkanbalci.travelguide.presentation.detail

import androidx.lifecycle.viewModelScope
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.data.repositories.DbRepository
import com.furkanbalci.travelguide.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DbRepository
) : BaseViewModel() {

    fun addBookmark(attraction: Attraction) {
        viewModelScope.launch {
            repository.insert(attraction)
        }
    }

    fun deleteBookmark(attractionId: String) {
        viewModelScope.launch {
            repository.delete(attractionId)
        }
    }

}