package com.example.testjobalef.model

import android.util.Log
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader

class PhotoRepository {
    suspend fun loadList(url: String): List<Photo> {

        Network.api.getFile(url).byteStream().use { inputStream ->
            val urlList =
                Json.decodeFromString<List<String>>(
                    inputStream.bufferedReader().use(BufferedReader::readText)
                )
            Log.d("list", urlList.toString())
            return urlList.map { url ->
                Photo(url)
            }
        }

    }

}