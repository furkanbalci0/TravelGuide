package com.furkanbalci.travelguide.presentation.guide.article

import android.app.Application
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.furkanbalci.travelguide.data.repositories.TriposoApiRepository
import com.furkanbalci.travelguide.di.DetailObject
import com.furkanbalci.travelguide.util.ResourceStatus
import com.furkanbalci.travelguide.vievmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleViewModel(application: Application) : BaseViewModel<List<DetailObject>>(application) {

    private var repository = TriposoApiRepository()

    init {
        this.refreshData()
    }

    private fun refreshData() {

        viewModelScope.launch {
            repository.getArticles().asLiveData(viewModelScope.coroutineContext).observeForever { it ->
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
                        success.postValue(it.data!!.results.filter {article ->  article.getOtherImages().isNotEmpty() })
                    }
                }
            }
        }
    }
}