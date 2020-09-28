package com.skillbox.github.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserToPatch(
    val name: String?,
    val email: String?,
    val company: String?,
    val location: String?,
    val hireable: Boolean?
)