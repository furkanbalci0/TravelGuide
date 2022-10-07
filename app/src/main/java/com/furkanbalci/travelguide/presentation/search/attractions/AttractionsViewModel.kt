package com.furkanbalci.travelguide.presentation.search.attractions

import android.app.Application
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.data.repositories.RoomRepository
import com.furkanbalci.travelguide.data.repositories.TriposoApiRepository
import com.furkanbalci.travelguide.util.PreferencesUtil
import com.furkanbalci.travelguide.util.PreferencesUtil.get
import com.furkanbalci.travelguide.util.PreferencesUtil.set
import com.furkanbalci.travelguide.util.ResourceStatus
import com.furkanbalci.travelguide.vievmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AttractionsViewModel(application: Application) : BaseViewModel<List<Attraction>>(application) {

    private var repository: TriposoApiRepository = TriposoApiRepository()
    private var roomRepository: RoomRepository = RoomRepository(application)

    init {
        val lastSelectedCountry = PreferencesUtil.defaultPrefs(application.applicationContext)["last-selected-country", "Amsterdam"]
        this.getData(lastSelectedCountry)
    }

    fun getData(city: String = "Amsterdam") {

        viewModelScope.launch(Dispatchers.Main) {
            repository.getAttractions(city).asLiveData(viewModelScope.coroutineContext).observeForever { it ->
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
                        launch(Dispatchers.Main) {
                            val bookmarks = roomRepository.getAllAttractions()
                            for (attraction in it.data!!.results) {
                                bookmarks.asSequence()
                                    .filter { bookmark -> attraction.id == bookmark.attractionId }
                                    .forEach { _ -> attraction.isBookmarked = true }
                            }
                        }
                        success.postValue(it.data!!.results.shuffled())


                    }
                }
            }
        }
    }

}