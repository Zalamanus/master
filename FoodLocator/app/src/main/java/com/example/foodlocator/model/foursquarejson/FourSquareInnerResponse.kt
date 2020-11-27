package com.example.foodlocator.model.foursquarejson

import kotlinx.serialization.Serializable

@Serializable
data class FourSquareInnerResponse(
    val totalResults: Int,
    val groups: List<FourSquareGroup>
)
