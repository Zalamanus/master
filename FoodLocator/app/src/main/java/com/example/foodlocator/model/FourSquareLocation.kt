package com.example.foodlocator.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FourSquareLocation (
    val address: String?,
    val lat: Double,
    val lng: Double
)
