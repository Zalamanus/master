package com.example.foodlocator.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

object Network {
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(AddAuthInterceptor())
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()
    private val contentType = "application/json".toMediaType()
    val format = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(format.asConverterFactory(contentType))
    .baseUrl("https://api.foursquare.com")
    .client(client)
    .build()

    val fourSquareApi: FourSquareApi
        get() = retrofit.create()

}

