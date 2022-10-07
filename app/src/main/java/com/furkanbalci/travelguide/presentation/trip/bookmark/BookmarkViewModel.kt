package com.furkanbalci.travelguide.presentation.trip.bookmark

import android.app.Application
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.data.repositories.RoomRepository
import com.furkanbalci.travelguide.data.repositories.TriposoApiRepository
import com.furkanbalci.travelguide.util.ResourceStatus
import com.furkanbalci.travelguide.vievmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarkViewModel(application: Application) : BaseViewModel<List<Attraction>>(application) {

    private var triposoRepository: TriposoApiRepository = TriposoApiRepository()
    private var roomRepository: RoomRepository = RoomRepository(application)

    init {
        this.loadData()
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.Main) {

            val bookmarkList = roomRepository.getBookmarks().map { it.attractionId }

            triposoRepository.findAttractions(*bookmarkList.toTypedArray()).asLiveData(viewModelScope.coroutineContext).observeForever { it ->
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

