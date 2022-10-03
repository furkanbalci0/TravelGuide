package com.furkanbalci.travelguide.presentation.home.deals

import android.app.Application
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.furkanbalci.travelguide.data.repositories.MockApiRepository
import com.furkanbalci.travelguide.di.DetailObject
import com.furkanbalci.travelguide.util.ResourceStatus
import com.furkanbalci.travelguide.vievmodel.BaseViewModel
import kotlinx.coroutines.launch

class HomeDealsViewModel(application: Application) : BaseViewModel<List<DetailObject>>(application) {

    private var repository = MockApiRepository()

    init {
        this.refreshData()
    }

    fun refreshData(category: String? = null) {

        viewModelScope.launch {
            repository.getResults(category).asLiveData(viewModelScope.coroutineContext).observeForever {
                when (it.status) {

                    ResourceStatus.LOADING -> {
                        loading.postValue(true)
                        error.postValue(false)
                    }

                    ResourceStatus.ERROR -> {
                        loading.postValue(false)
                        error.postValue(true)
                    }

                    ResourceStatus.SUCCESS -> {
                        loading.postValue(false)
                        error.postValue(false)
                        success.postValue(it.data)
                    }
                }
            }
        }
    }
}