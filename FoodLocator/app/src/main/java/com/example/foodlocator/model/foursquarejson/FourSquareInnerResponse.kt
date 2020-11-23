package com.example.foodlocator.model.foursquarejson

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FourSquareInnerResponse(
    val totalResults: Int,
    val groups: List<FourSquareGroup>
)
