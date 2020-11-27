package com.example.foodlocator.model.foursquarejson

import kotlinx.serialization.Serializable

@Serializable
class FourSquareLocation (
    val address: String? = null,
    val lat: Double,
    val lng: Double
)
