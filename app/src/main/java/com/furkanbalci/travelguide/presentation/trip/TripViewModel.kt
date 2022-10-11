package com.furkanbalci.travelguide.presentation.trip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.furkanbalci.travelguide.data.models.Trip
import com.furkanbalci.travelguide.data.repositories.DbRepository
import com.furkanbalci.travelguide.presentation.base.BaseViewModel
import com.furkanbalci.travelguide.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripViewModel @Inject constructor(private val dbRepository: DbRepository) : BaseViewModel() {

    var tripLiveData = MutableLiveData<List<Trip>>()
    var listUpdateLiveData = MutableLiveData<Boolean>()
    var listRemoveLiveData = MutableLiveData<Boolean>()

    init {
        getTrips()
    }

    /**
     * Refresh data.
     */
    fun getTrips() {

        viewModelScope.launch(Dispatchers.Main) {

            //Get trips.
            dbRepository.getTrips().collect() {

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
                            tripLiveData.postValue(list)
                        }
                    }
                }
            }
        }
    }

    fun addTrip(trip: Trip) {
        viewModelScope.launch(Dispatchers.Main) {

            //Get trips.
            dbRepository.insert(trip).collect() {

                println("AAAAAAAAAAAAAA: 0")
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
                        println("AAAAAAAAAAAAAA: 1")
                        listUpdateLiveData.postValue(true)
                    }
                }
            }
        }
    }

    fun deleteTrip(tripId: String) {
        viewModelScope.launch(Dispatchers.Main) {

            //Get trips.
            dbRepository.deleteTrip(tripId).collect() {

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
                        listRemoveLiveData.postValue(true)
                    }
                }
            }
        }
    }
}

