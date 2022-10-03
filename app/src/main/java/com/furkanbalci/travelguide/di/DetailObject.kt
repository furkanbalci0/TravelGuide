package com.furkanbalci.travelguide.di

import java.io.Serializable

interface DetailObject : Serializable {

    fun getOtherImages(): List<String>

    fun mainImageUrl(): String

    fun name(): String

    fun description(): String

    fun miniDescription(): String
}