package com.example.moshi.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    val title: String,
    val year: String,
    val rate: MovieRate = MovieRate.GENERAL,
    val id: String,
    val genre: String,
    val poster: String,
    val scores: MutableMap<String, String> = mutableMapOf()

)