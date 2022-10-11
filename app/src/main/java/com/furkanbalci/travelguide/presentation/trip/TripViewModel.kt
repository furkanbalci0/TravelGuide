package com.furkanbalci.travelguide.presentation.trip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.furkanbalci.travelguide.data.models.Trip
import com.furkanbalci.travelguide.data.models.attractions.Attraction
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

    init {
        getTrips()
    }

    /**
     * Refresh data.
     */
    private fun getTrips() {

        //Get attractions.
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
}

