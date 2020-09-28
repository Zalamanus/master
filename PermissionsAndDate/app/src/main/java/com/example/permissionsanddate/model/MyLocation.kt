package com.example.permissionsanddate.model

import android.location.Location
import org.threeten.bp.Instant
import kotlin.random.Random

data class MyLocation(
    var time: Instant,
    val data: Location
    ) {
    val id: Long = Random.nextLong()
    val imageLink = imageLinks.random()
        companion object {
        val imageLinks = arrayOf(
            "https://upload.wikimedia.org/wikipedia/commons/b/b3/Small_map.jpg",
            "https://bethel-university.imgix.net/about/maps-directions/images/map--3900.jpg?w=200",
            "https://bethel-university.imgix.net/about/maps-directions/images/map--anderson.jpg?w=200"
        )
    }
}