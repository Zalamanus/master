package com.example.foodlocator.model

import android.util.Log
import com.example.foodlocator.data.Network
import com.example.foodlocator.model.foursquarejson.FourSquareResponse
import com.example.foodlocator.model.foursquarejson.FourSquareVenue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FourSquareRepository {
    suspend fun getVenues(ll: String, section: String, radius: Int?): Set<FourSquareVenue> {
        val venuesSet = mutableSetOf<FourSquareVenue>()
        withContext(Dispatchers.IO) {
            try {
                val response = Network.fourSquareApi.makeRequest(ll, section, radius)
                if (response.meta?.code == 200) {
                    response.response?.groups!!.first().items.forEach {
                        venuesSet.add(it.venue)
                    }
                } else {
                    error("network error")
                }
            } catch (e: Throwable) {
                Log.d("FourSquareRepository", e.message ?: "something wrong")
            }


        }
        return venuesSet

    }

}