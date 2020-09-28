package com.example.moshi.network

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor

object Network {

    private const val MOVIE_API_KEY = "9bdbbf61"

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(
            AddApiKeyInterceptor(
                MOVIE_API_KEY
            )
        )
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    fun searchMovie(
        keyWords: String
    ): String {
        val urlBuilder = HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            .addQueryParameter("t", keyWords)
        val url = urlBuilder.build()

        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        val response = client.newCall(request).execute()
        return (response.body?.string().orEmpty())
    }


}