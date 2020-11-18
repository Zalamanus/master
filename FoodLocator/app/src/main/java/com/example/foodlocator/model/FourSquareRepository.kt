package com.example.foodlocator.model

import android.util.Log
import com.example.foodlocator.data.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FourSquareRepository {
    suspend fun getVenues(near: String, section: String): List<FourSquareVenue> {
        val venuesList = mutableListOf<FourSquareVenue>()
        withContext(Dispatchers.IO) {
            var response: FourSquareResponse? = null
            try {
                response = Network.fourSquareApi.makeRequest(near, section)
            } catch (e: Throwable) {
                Log.d("FourSquareRepository", e.message ?: "something wrong")
            }
            if (response?.meta!!.code == 200) {
                response.response?.groups!![0].items.forEach {
                    venuesList.add(it.venue)
                }

            } else {
                error("network error")
            }

        }
        return venuesList

    }

}