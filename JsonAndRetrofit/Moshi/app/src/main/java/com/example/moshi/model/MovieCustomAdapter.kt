package com.example.moshi.model

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson


class MovieCustomAdapter {
    @FromJson
    fun fromJson(remoteMovie: RemoteMovie): Movie {
        val scoresMap: MutableMap<String, String> = mutableMapOf()
        remoteMovie.scores?.forEach {
            scoresMap[it.source] = it.value
        }
        return Movie(
            remoteMovie.title,
            remoteMovie.year,
            remoteMovie.rate,
            remoteMovie.id,
            remoteMovie.genre,
            remoteMovie.poster,
            scoresMap
        )
    }

    @ToJson
    fun toJson(movie: Movie): RemoteMovie {
        val scoresList = mutableListOf<Score>()
        movie.scores.forEach {
            scoresList.add(Score(it.key, it.value))
        }
        return RemoteMovie(
            title = movie.title,
            year = movie.year,
            rate = movie.rate,
            id = movie.id,
            genre = movie.genre,
            poster = movie.poster,
            scores = scoresList
        )
    }

    @JsonClass(generateAdapter = true)
    data class RemoteMovie(
        @Json(name = "Title")
        val title: String,
        @Json(name = "Year")
        val year: String,
        @Json(name = "Rated")
        val rate: MovieRate = MovieRate.GENERAL,
        @Json(name = "imdbID")
        val id: String,
        @Json(name = "Genre")
        val genre: String,
        @Json(name = "Poster")
        val poster: String,
        @Json(name = "Ratings")
        val scores: List<Score>? = emptyList()
    )
}