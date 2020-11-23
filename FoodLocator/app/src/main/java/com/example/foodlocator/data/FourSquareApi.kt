package com.example.foodlocator.data

import com.example.foodlocator.model.foursquarejson.FourSquareResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FourSquareApi {
    @GET("v2/venues/explore")
    suspend fun makeRequest(
        @Query("ll") near: String,
        @Query("section") section: String,
        @Query("radius") radius: Int?
    ): FourSquareResponse
}