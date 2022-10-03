package com.furkanbalci.travelguide.data.models

import com.furkanbalci.travelguide.data.models.country.Country
import com.furkanbalci.travelguide.di.DetailObject

data class Destination(
    val country: Country
) : DetailObject {
    override fun getOtherImages(): List<String> {
        return country.images
            .asSequence()
            .map { it.sizes }
            .map { it.medium.url }
            .toList()
    }

    override fun mainImageUrl(): String {
        return this.country.images[0].sizes.medium.url
    }

    override fun name(): String {
        return this.country.countryId.replace("_", " ")
    }

    override fun description(): String {
        return this.country.name
    }

    override fun miniDescription(): String {
        return this.country.name
    }
}