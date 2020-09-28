package com.skillbox.github.data

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


object Network {

    private lateinit var client: OkHttpClient
    private lateinit var retrofit: Retrofit

    var token: String = ""
        set(value) {
            client = OkHttpClient.Builder()
                .addNetworkInterceptor(
                    AddTokenInterceptor(
                        value
                    )
                )
                .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                })
                .build()

            retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("https://api.github.com")
                .client(client)
                .build()
        }


    val gitHubApi: GitHubApi
        get() = retrofit.create()


}