package com.skillbox.github.data

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddTokenInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        return if (token.isNotEmpty()) {
            val modifiedRequest: Request = originalRequest.newBuilder()
                .headers(
                    originalRequest.headers.newBuilder()
                        .add("Authorization", "token $token")
                        .build()
                )
                .build()
            chain.proceed(modifiedRequest)
        } else chain.proceed(originalRequest)
    }
}