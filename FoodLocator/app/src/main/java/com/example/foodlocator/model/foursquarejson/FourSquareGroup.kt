package com.example.foodlocator.model.foursquarejson

import kotlinx.serialization.Serializable

@Serializable
data class FourSquareGroup (
    val type: String,
    val name: String,
    val items: List<FourSquareItem>
)
