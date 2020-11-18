package com.example.foodlocator.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FourSquareMeta (
    val code: Int
)