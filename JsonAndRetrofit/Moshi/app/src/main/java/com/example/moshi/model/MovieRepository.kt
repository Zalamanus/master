package com.example.moshi.model

import android.util.Log
import com.example.moshi.network.Network
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi

class MovieRepository {
    private var movie: Movie? = null
    private val moshi: Moshi = Moshi.Builder()
        .add(MovieCustomAdapter())
        .build()
    private val adapter: JsonAdapter<Movie> = moshi.adapter<Movie>(Movie::class.java).nonNull()

    fun fetchMovie(
        keyWords: String,
        onMovieFetched: (movie: Movie?) -> Unit,
        onError: (error: String?) -> Unit
    ) {
        movie = null
        val backgroundThread = Thread {
            try {
                movie = parseMovieResponse(Network.searchMovie(keyWords))
            } catch (e: Exception) {
                onError(e.message)
            }
        }
        backgroundThread.start()
        backgroundThread.join()
        movie?.also(onMovieFetched)

    }


    private fun parseMovieResponse(bodyString: String): Movie {

        val movieToReturn: Movie?
        try {
            movieToReturn = adapter.fromJson(bodyString)
        } catch (e: JsonDataException) {
            Log.d("Parsing",e.message ?: "message isn't exist")
            throw Exception("Movie is not found")
        }
        return movieToReturn
            ?: throw Exception("Movie is null")

    }

    fun saveMovie(movieToSave: Movie) {
        Log.d("Repository", "Sending movie to server ${adapter.toJson(movieToSave)}")

    }
}