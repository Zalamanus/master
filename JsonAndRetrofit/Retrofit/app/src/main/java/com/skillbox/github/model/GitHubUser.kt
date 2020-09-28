package com.skillbox.github.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

/*
{
   "login":"Zalamanus",
   "id":22361897,
   "node_id":"MDQ6VXNlcjIyMzYxODk3",
   "avatar_url":"https://avatars2.githubusercontent.com/u/22361897?v=4",
   "gravatar_id":"",
   "url":"https://api.github.com/users/Zalamanus",
   "html_url":"https://github.com/Zalamanus",
   "followers_url":"https://api.github.com/users/Zalamanus/followers",
   "following_url":"https://api.github.com/users/Zalamanus/following{/other_user}",
   "gists_url":"https://api.github.com/users/Zalamanus/gists{/gist_id}",
   "starred_url":"https://api.github.com/users/Zalamanus/starred{/owner}{/repo}",
   "subscriptions_url":"https://api.github.com/users/Zalamanus/subscriptions",
   "organizations_url":"https://api.github.com/users/Zalamanus/orgs",
   "repos_url":"https://api.github.com/users/Zalamanus/repos",
   "events_url":"https://api.github.com/users/Zalamanus/events{/privacy}",
   "received_events_url":"https://api.github.com/users/Zalamanus/received_events",
   "type":"User",
   "site_admin":false,
   "name":null,
   "company":null,
   "blog":"",
   "location":null,
   "email":null,
   "hireable":null,
   "bio":null,
   "twitter_username":null,
   "public_repos":22,
   "public_gists":0,
   "followers":0,
   "following":0,
   "created_at":"2016-09-22T04:10:53Z",
   "updated_at":"2020-09-04T08:51:04Z",
   "private_gists":0,
   "total_private_repos":0,
   "owned_private_repos":0,
   "disk_usage":25457,
   "collaborators":0,
   "two_factor_authentication":false,
   "plan":{
      "name":"free",
      "space":976562499,
      "collaborators":0,
      "private_repos":10000
   }
}

 */

@JsonClass(generateAdapter = true)
data class GitHubUser(
    val login: String,
    val name: String?,
    val company: String?,
    val id: Int,
    val avatar_url: String,
    val location: String?,
    val email: String?,
    val public_repos: Int?,
    val hireable: Boolean?
): Serializable