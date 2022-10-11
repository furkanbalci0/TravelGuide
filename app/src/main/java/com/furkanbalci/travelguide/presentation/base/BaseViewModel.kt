package com.furkanbalci.travelguide.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

/**
 * Base view model.
 */
open class BaseViewModel : ViewModel() {

    //Base view model variables.
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

}