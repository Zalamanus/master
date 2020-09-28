package com.skillbox.github.model

import android.util.Log
import com.skillbox.github.App
import com.skillbox.github.R
import com.skillbox.github.data.Network
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import okio.BufferedSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resumeWithException

class GitHubRepository {

    private val moshi: Moshi = Moshi.Builder().build()
    private val adapter: JsonAdapter<GitHubErrorResponse> =
        moshi.adapter(GitHubErrorResponse::class.java).nonNull()


    suspend fun getPublicRepos(): List<GitHubRepo> {
        lateinit var reposList: List<GitHubRepo>
        withContext(Dispatchers.IO) {
            val response = Network.gitHubApi.getReposList().execute()
            if (response.isSuccessful) {
                reposList = response.body()!!
            } else {
                error(
                    parseGitHubErrorResponse(response.errorBody()?.source()!!)
                        ?: App.context!!.getString(R.string.wrong_server_response)
                )
            }

        }
        return reposList

    }

    suspend fun getCurrentUser(): GitHubUser {
        return try {
            Network.gitHubApi.getCurrentUser()
        } catch (t: HttpException) {
            throw(RuntimeException(parseGitHubErrorResponse(t.response()?.errorBody()?.source()!!)
                ?: App.context!!.getString(R.string.wrong_server_response))
                    )
        }
    }

    suspend fun getFollowingUsers(): List<GitHubUser> {
        return try {
            Network.gitHubApi.getFollowingUsers()
        } catch (t: HttpException) {
            throw(RuntimeException(parseGitHubErrorResponse(t.response()?.errorBody()?.source()!!)
                ?: App.context!!.getString(R.string.wrong_server_response))
                    )
        }
    }

    @ExperimentalCoroutinesApi
    suspend fun checkIfRepoStarred(
        owner: String,
        repo: String,
    ): Boolean {
        return suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation {
                Network.gitHubApi.checkIfRepoStarred(owner, repo).cancel()
            }
            Network.gitHubApi.checkIfRepoStarred(owner, repo).enqueue(
                object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                        continuation.resumeWithException(
                            RuntimeException(App.context!!.getString(R.string.error_internet_connection))
                        )
                    }

                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    ) {
                        return when (response.code()) {
                            204 -> continuation.resume(true) { throw it }
                            404 -> continuation.resume(false) { throw it }
                            else -> {
                                continuation.resumeWithException(
                                    RuntimeException(
                                        parseGitHubErrorResponse(response.errorBody()?.source()!!)
                                            ?: App.context!!.getString(R.string.wrong_server_response)
                                    )
                                )
                            }
                        }
                    }
                }
            )
        }

    }

    suspend fun starRepo(
        owner: String,
        repo: String
    ) {
        withContext(Dispatchers.Default) {
            val response = Network.gitHubApi.starRepo(owner, repo)
            if (response.code() != 204)
                throw (RuntimeException(
                    parseGitHubErrorResponse(response.errorBody()?.source()!!)
                        ?: App.context!!.getString(R.string.wrong_server_response)
                ))
        }
    }

    suspend fun unStarRepo(
        owner: String,
        repo: String
    ) {
        withContext(Dispatchers.Default) {
            val response = Network.gitHubApi.unStarRepo(owner, repo)
            throw (RuntimeException(
                parseGitHubErrorResponse(response.errorBody()?.source()!!)
                    ?: App.context!!.getString(R.string.wrong_server_response)
            ))
        }
    }

    suspend fun updateUser(user: UserToPatch): GitHubUser {
        return try {
            Network.gitHubApi.updateUser(user)
        } catch (t: HttpException) {
            throw(RuntimeException(parseGitHubErrorResponse(t.response()?.errorBody()?.source()!!)
                ?: App.context!!.getString(R.string.wrong_server_response))
                    )
        }
    }

    private fun parseGitHubErrorResponse(bufferedSource: BufferedSource): String? {
        var errorResponse: GitHubErrorResponse? = null
        try {
            errorResponse = adapter.fromJson(bufferedSource)
        } catch (e: Exception) {
            Log.d("Moshi parser", e.message.orEmpty())
        }
        var errorMessage: String? = null
        errorResponse?.let { githubResponse ->
            errorMessage = githubResponse.message
            githubResponse.errors?.forEach { githubError ->
                errorMessage += "\n ${githubError.message}"
            }
        }
        return errorMessage
    }

}