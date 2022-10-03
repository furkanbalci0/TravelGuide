package com.furkanbalci.travelguide.presentation.search.destination

import android.app.Application
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.furkanbalci.travelguide.data.models.Destination
import com.furkanbalci.travelguide.data.repositories.RapidApiRepository
import com.furkanbalci.travelguide.util.ResourceStatus
import com.furkanbalci.travelguide.vievmodel.BaseViewModel
import kotlinx.coroutines.launch

class DestinationsViewModel(application: Application) : BaseViewModel<List<Destination>>(application) {

    private var repository: RapidApiRepository = RapidApiRepository()

    init {
        this.getData()
    }

    private fun getData() {

        viewModelScope.launch {
            repository.getCountries().asLiveData(viewModelScope.coroutineContext).observeForever {
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

                        //Convert to Destination
                        success.postValue(it.data!!.results.map { country -> Destination(country) })
                    }
                }
            }
        }
    }

}