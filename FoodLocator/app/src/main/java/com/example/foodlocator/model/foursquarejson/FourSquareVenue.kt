package com.example.foodlocator.model.foursquarejson

import kotlinx.serialization.Serializable

@Serializable
data class FourSquareVenue (
    val id: String,
    val name: String,
    val location: FourSquareLocation
)
