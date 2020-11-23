package com.example.foodlocator.model.foursquarejson

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FourSquareVenue (
    val id: String,
    val name: String,
    val location: FourSquareLocation
)
