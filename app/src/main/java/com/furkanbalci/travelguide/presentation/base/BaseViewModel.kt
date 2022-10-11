package com.furkanbalci.travelguide.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Base view model.
 */
open class BaseViewModel : ViewModel() {

    //Base view model variables.
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

}