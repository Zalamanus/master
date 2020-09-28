package com.example.networking.paging

import com.example.networking.model.Movie

data class MovieSearchResponse (
        val total: Int = 0,
        val items: List<Movie> = emptyList(),
        val nextPage: Int? = null
)