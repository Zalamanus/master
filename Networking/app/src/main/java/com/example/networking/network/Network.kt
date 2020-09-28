package com.example.networking.network

import com.example.networking.model.Movie
import com.example.networking.paging.MovieSearchResponse
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await

object Network {

    const val MOVIE_API_KEY = "9bdbbf61"
    val flipperNetworkPlugin = NetworkFlipperPlugin()

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(
            AddApiKeyInterceptor(
                MOVIE_API_KEY
            )
        )
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .addNetworkInterceptor(FlipperOkhttpInterceptor(flipperNetworkPlugin))
        .build()

    suspend fun searchMovie(
        keyWords: String,
        year: String,
        movieType: String,
        page: Int
    ): MovieSearchResponse {

        val urlBuilder = HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            .addQueryParameter("s", keyWords)
            .addQueryParameter("page", page.toString())
        if (year.isNotEmpty()) urlBuilder.addQueryParameter("y", year)
        if (movieType.isNotEmpty()) urlBuilder.addQueryParameter("type", movieType)
        val url = urlBuilder.build()

        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        val response = client.newCall(request).await()
        return parseMovieResponse(response.body?.string().orEmpty(),page)
    }

    fun parseMovieResponse(bodyString: String, page: Int): MovieSearchResponse {
//        try {
            val jsonObject = JSONObject(bodyString)
            val movieArray = jsonObject.getJSONArray("Search")
            val totalItems = jsonObject.getInt("totalResults")
            val movies = (0 until movieArray.length())
                .map { pos ->
                    movieArray.getJSONObject(pos)
                }
                .map { movieObject ->
                    val title = movieObject.getString("Title")
                    val year = movieObject.getString("Year")
                    val id = movieObject.getString("imdbID")
                    val type = when (movieObject.getString("Type")) {
                        "series" -> 1
                        "movie" -> 2
                        "episode" -> 3
                        "game" -> 4
                        else -> 0
                    }

                    val poster = movieObject.getString("Poster")
                    Movie(
                        title,
                        year,
                        id,
                        type,
                        poster
                    )
                }
            return MovieSearchResponse(totalItems, movies, page + 1)


    }

}