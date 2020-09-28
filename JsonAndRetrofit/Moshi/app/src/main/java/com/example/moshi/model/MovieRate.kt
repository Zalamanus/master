package com.example.moshi.model

import com.squareup.moshi.Json

enum class MovieRate(val desc: String) {
    @Json(name = "G")
    GENERAL("Общедоступный фильм"),
    PG("Просмотр с родителями"),
    @Json(name = "PG-13")
    PG_13("С 13 лет, просмотр с родителями"),
    R("С 17 лет, просмотр с родителями"),
    @Json(name = "NC-17")
    NC_17("С 18 лет"),
    @Json(name = "N/A")
    NA("Ограничение неизвестно")
}
