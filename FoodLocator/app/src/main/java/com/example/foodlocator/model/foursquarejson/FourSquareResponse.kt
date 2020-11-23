package com.example.foodlocator.model.foursquarejson

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FourSquareResponse (
    val meta: FourSquareMeta,
    val response: FourSquareInnerResponse?
)