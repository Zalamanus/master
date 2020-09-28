package com.example.moshi.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val modifiedRequest: Request = originalRequest.newBuilder()
            .url(
                originalRequest.url.newBuilder()
                    .addQueryParameter("apikey", apiKey)
                    .build()
            )
            .build()
        return chain.proceed(modifiedRequest)
    }
}