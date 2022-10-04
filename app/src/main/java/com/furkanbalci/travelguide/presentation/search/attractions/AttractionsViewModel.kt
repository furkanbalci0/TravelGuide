package com.furkanbalci.travelguide.presentation.search.attractions

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.data.repositories.TriposoApiRepository
import com.furkanbalci.travelguide.util.ResourceStatus
import com.furkanbalci.travelguide.vievmodel.BaseViewModel
import kotlinx.coroutines.launch

class AttractionsViewModel(application: Application) : BaseViewModel<List<Attraction>>(application) {

    private var repository: TriposoApiRepository = TriposoApiRepository()
    var preferencesLiveData = MutableLiveData<String>()

    init {
        this.getData()
    }

    fun getData(city: String = "Amsterdam") {

        viewModelScope.launch {
            repository.getAttractions(city).asLiveData(viewModelScope.coroutineContext).observeForever {
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
                        success.postValue(it.data!!.results)
                    }
                }
            }
        }
    }

}