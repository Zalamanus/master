package com.example.foodlocator.model.foursquarejson

import kotlinx.serialization.Serializable

@Serializable
data class FourSquareItem (
    val venue: FourSquareVenue
)
