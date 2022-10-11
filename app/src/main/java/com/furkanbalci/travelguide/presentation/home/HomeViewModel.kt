package com.furkanbalci.travelguide.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.furkanbalci.travelguide.data.models.MockObject
import com.furkanbalci.travelguide.data.repositories.MockApiRepository
import com.furkanbalci.travelguide.presentation.base.BaseViewModel
import com.furkanbalci.travelguide.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MockApiRepository
) : BaseViewModel() {

    var attractionsLiveData = MutableLiveData<List<MockObject>>()

    init {
        refreshData()
    }

    /**
     * Refresh data from repository.
     * @param category Category name.
     */
    fun refreshData(category: String? = null) {

        viewModelScope.launch {
            repository.getResults(category).collect { it ->

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
                            attractionsLiveData.postValue(list)
                        }

                    }
                }
            }
        }
    }
}