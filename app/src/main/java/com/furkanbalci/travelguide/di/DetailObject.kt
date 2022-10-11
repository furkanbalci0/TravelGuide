package com.furkanbalci.travelguide.di

import java.io.Serializable

/**
 * Interface for model objects.
 */
interface DetailObject : Serializable {

    /**
     * Gets other image links of the object.
     * @return A list of image links.
     */
    fun getOtherImages(): List<String>

    /**
     * Gets main image link of the object.
     * @return A main image link.
     */
    fun mainImageUrl(): String

    /**
     * Gets the name of the object like title.
     * @return A name of the object.
     */
    fun customName(): String

    /**
     * Gets the main description of the object.
     * @return A main description of the object.
     */
    fun description(): String

    /**
     * Gets the mini description of the object.
     * @return A mini description of the object.
     */
    fun miniDescription(): String

    /**
     * Gets the custom id of the object.
     * @return A custom id of the object.
     */
    fun getCustomId(): String
}