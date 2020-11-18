package com.example.foodlocator.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Network {
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(AddAuthInterceptor())
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()
    private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl("https://api.foursquare.com")
    .client(client)
    .build()

    val fourSquareApi: FourSquareApi
        get() = retrofit.create()

}

