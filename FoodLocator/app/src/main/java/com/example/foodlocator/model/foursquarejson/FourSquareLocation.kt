package com.example.foodlocator.model.foursquarejson

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FourSquareLocation (
    val address: String?,
    val lat: Double,
    val lng: Double
)
