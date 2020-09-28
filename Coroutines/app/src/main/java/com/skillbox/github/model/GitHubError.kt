package com.skillbox.github.model

import com.squareup.moshi.JsonClass

/*
 {
         "resource":"User",
         "code":"custom",
         "field":"profile_email",
         "message":"profile_email must be one of the user's verified email addresses"
      }
 */
@JsonClass(generateAdapter = true)
data class GitHubError(
    val resource: String,
    val code: String,
    val field: String,
    val message: String
)