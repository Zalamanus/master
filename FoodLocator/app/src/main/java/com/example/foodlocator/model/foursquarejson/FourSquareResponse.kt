package com.example.foodlocator.model.foursquarejson

import kotlinx.serialization.Serializable

@Serializable
data class FourSquareResponse (
    val meta: FourSquareMeta? = null,
    val response: FourSquareInnerResponse?
)