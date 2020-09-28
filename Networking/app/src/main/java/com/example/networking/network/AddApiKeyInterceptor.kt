package com.example.networking.network

import okhttp3.Interceptor
import okhttp3.Response

class AddApiKeyInterceptor(val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .url(
                originalRequest.url.newBuilder()
                    .addQueryParameter("apikey", apiKey)
                    .build()
            )
            .build()
        return chain.proceed(modifiedRequest)
    }
}