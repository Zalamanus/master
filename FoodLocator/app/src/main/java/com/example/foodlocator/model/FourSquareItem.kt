package com.example.foodlocator.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FourSquareItem (
    val venue: FourSquareVenue
)
