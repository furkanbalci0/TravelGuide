package com.furkanbalci.travelguide.presentation.guide

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.furkanbalci.travelguide.data.repositories.TriposoApiRepository
import com.furkanbalci.travelguide.di.DetailObject
import com.furkanbalci.travelguide.presentation.base.BaseViewModel
import com.furkanbalci.travelguide.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuideViewModel @Inject constructor(
    private val repository: TriposoApiRepository
) :
    BaseViewModel() {

    val articlesLiveData = MutableLiveData<List<DetailObject>>()

    init {
        refreshData()
    }

    /**
     * Refresh data from repository.
     * @param city City name.
     */
    fun refreshData(city: String = "Istanbul") {

        viewModelScope.launch {
            repository.getArticles(city).collect {

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
                        articlesLiveData.postValue(it.data!!.results.filter { article -> article.getOtherImages().isNotEmpty() })
                    }
                }
            }
        }
    }
}