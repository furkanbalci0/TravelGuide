package com.furkanbalci.travelguide.util

/**
 * Resource is a generic class that holds a value with its loading status.
 * @param <T> the type of the resource data.
 */

sealed class Resource<out T> {
    data class Loading(val loading: Boolean) : Resource<Nothing>()
    data class Error(val errorMessage: String?) : Resource<Nothing>()
    data class Success<T>(val data: T?) : Resource<T>()
}
