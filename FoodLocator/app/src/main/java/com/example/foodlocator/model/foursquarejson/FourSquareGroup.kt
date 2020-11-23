package com.example.foodlocator.model.foursquarejson

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FourSquareGroup (
    val type: String,
    val name: String,
    val items: List<FourSquareItem>
)
