package com.example.foodlocator.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FourSquareVenue (
    val id: String,
    val name: String,
    val location: FourSquareLocation
)
