package com.example.foodlocator.data

import com.example.foodlocator.model.FourSquareResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FourSquareApi {
    @GET("v2/venues/explore")
    suspend fun makeRequest(
        @Query("near") near: String,
        @Query("section") section: String
    ): FourSquareResponse
}