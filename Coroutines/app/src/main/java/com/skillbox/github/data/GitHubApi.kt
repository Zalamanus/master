package com.skillbox.github.data

import com.skillbox.github.model.GitHubRepo
import com.skillbox.github.model.GitHubUser
import com.skillbox.github.model.UserToPatch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface GitHubApi {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("user")
    suspend fun getCurrentUser(): GitHubUser

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("user/following")
    suspend fun getFollowingUsers(): List<GitHubUser>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("repositories")
    fun getReposList(): Call<List<GitHubRepo>>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user/starred/{owner}/{repo}")
    fun checkIfRepoStarred(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<ResponseBody>

    @Headers("Accept: application/vnd.github.v3+json")
    @PUT("/user/starred/{owner}/{repo}")
    suspend fun starRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<Unit>

    @Headers("Accept: application/vnd.github.v3+json")
    @DELETE("/user/starred/{owner}/{repo}")
    suspend fun unStarRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<Unit>

    @Headers("Accept: application/vnd.github.v3+json")
    @PATCH("user")
    suspend fun updateUser(
        @Body user: UserToPatch
    ): GitHubUser

}