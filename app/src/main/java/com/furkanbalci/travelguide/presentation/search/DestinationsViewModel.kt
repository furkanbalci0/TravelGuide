package com.furkanbalci.travelguide.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.furkanbalci.travelguide.data.models.Destination
import com.furkanbalci.travelguide.data.models.Trip
import com.furkanbalci.travelguide.data.repositories.DbRepository
import com.furkanbalci.travelguide.data.repositories.TriposoApiRepository
import com.furkanbalci.travelguide.presentation.base.BaseViewModel
import com.furkanbalci.travelguide.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinationsViewModel @Inject constructor(
    private val repository: TriposoApiRepository,
    private val dbRepository: DbRepository
) : BaseViewModel() {

    val destinationLiveData = MutableLiveData<List<Destination>>()

    init {
        getData()
    }

    private fun getData() {

        viewModelScope.launch(Dispatchers.Main) {
            repository.getCountries().collect {

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
                        val list = it.data!!.results.map { country -> Destination(country) }
                        destinationLiveData.postValue(list.shuffled())
                    }
                }

            }
        }
    }

    //TODO: BURAYA BAK
    fun addTrip(trip: Trip) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.insert(trip)
        }
    }

}