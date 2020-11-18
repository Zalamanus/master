package com.example.foodlocator.data

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val modifiedRequest: Request = originalRequest.newBuilder()
            .url(
                originalRequest.url.newBuilder()
                    .addQueryParameter("client_id", CLIENT_ID)
                    .addQueryParameter("client_secret", CLIENT_SECRET)
                    .addQueryParameter("v", VERSION)
                    .build()
            )
            .build()
        return chain.proceed(modifiedRequest)
    }

    companion object {
        const val CLIENT_ID = "5IHWKUMKBY5AOAZU51DMXZQ5RDVRLUN1SX43NSK3OEUN1ADG"
        const val CLIENT_SECRET = "LLBUYGSWUFTY2NDBL3OOOIAOO4C4251302G14UM221MJPAGC"
        const val VERSION = "20201116"
    }

}
