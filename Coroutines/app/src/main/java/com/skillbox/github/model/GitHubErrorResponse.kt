package com.skillbox.github.model

import com.squareup.moshi.JsonClass

/*
{
   "message":"Validation Failed",
   "errors":[
      {
         "resource":"User",
         "code":"custom",
         "field":"profile_email",
         "message":"profile_email must be one of the user's verified email addresses"
      }
   ],
   "documentation_url":"https://docs.github.com/rest/reference/users#update-the-authenticated-user"
}
 */
@JsonClass(generateAdapter = true)
data class GitHubErrorResponse(
    val message: String,
    val errors: List<GitHubError>?
)