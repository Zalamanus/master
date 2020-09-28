package com.skillbox.github.model

import android.util.Log
import com.skillbox.github.App
import com.skillbox.github.R
import com.skillbox.github.data.Network
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitHubRepository {

    private val moshi: Moshi = Moshi.Builder().build()
    private val adapter: JsonAdapter<GitHubErrorResponse> =
        moshi.adapter(GitHubErrorResponse::class.java).nonNull()


    fun getPublicRepos(onResponse: (List<GitHubRepo>) -> Unit, onError: (String) -> Unit) {
        Network.gitHubApi.getReposList().enqueue(
            object : Callback<List<GitHubRepo>> {
                override fun onFailure(call: Call<List<GitHubRepo>>, t: Throwable) {
                    onError(App.context!!.getString(R.string.error_internet_connection))
                }

                override fun onResponse(
                    call: Call<List<GitHubRepo>>,
                    response: Response<List<GitHubRepo>>
                ) {
                    if (response.isSuccessful) {
                        onResponse(response.body()!!)
                    } else {
                        onError(
                            parseGitHubErrorResponse(response.errorBody()?.string())
                                ?: App.context!!.getString(R.string.wrong_server_response)
                        )
                    }
                }

            }
        )
    }

    fun getCurrentUser(onResponse: (GitHubUser) -> Unit, onError: (String) -> Unit) {
        Network.gitHubApi.getCurrentUser().enqueue(
            object : Callback<GitHubUser> {
                override fun onFailure(call: Call<GitHubUser>, t: Throwable) {
                    onError(App.context!!.getString(R.string.error_internet_connection))
                }

                override fun onResponse(call: Call<GitHubUser>, response: Response<GitHubUser>) {
                    if (response.isSuccessful) {
                        onResponse(response.body()!!)
                    } else {
                        onError(
                            parseGitHubErrorResponse(response.errorBody()?.string())
                                ?: App.context!!.getString(R.string.wrong_server_response)
                        )
                    }
                }

            }
        )
    }

    fun checkIfRepoStarred(
        owner: String,
        repo: String,
        onResponse: (Boolean?) -> Unit,
        onError: (String) -> Unit
    ) {
        Network.gitHubApi.checkIfRepoStarred(owner, repo).enqueue(
            object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    onError(App.context!!.getString(R.string.error_internet_connection))
                }

                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    return when (response.code()) {
                        204 -> onResponse(true)
                        404 -> onResponse(false)
                        else -> {
                            onError(
                                parseGitHubErrorResponse(response.errorBody()?.string())
                                    ?: App.context!!.getString(R.string.wrong_server_response)
                            )
                        }
                    }
                }
            }
        )
    }

    fun starRepo(
        owner: String,
        repo: String,
        onResponse: () -> Unit,
        onError: (String) -> Unit
    ) {
        Network.gitHubApi.starRepo(owner, repo).enqueue(
            object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    onError(App.context!!.getString(R.string.error_internet_connection))
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        204 -> onResponse()
                        else -> {
                            onError(
                                parseGitHubErrorResponse(response.errorBody()?.string())
                                    ?: App.context!!.getString(R.string.wrong_server_response)
                            )
                        }
                    }
                }
            }
        )
    }

    fun unStarRepo(
        owner: String,
        repo: String,
        onResponse: () -> Unit,
        onError: (String) -> Unit
    ) {
        Network.gitHubApi.unStarRepo(owner, repo).enqueue(
            object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    onError(App.context!!.getString(R.string.error_internet_connection))
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    when (response.code()) {
                        204 -> onResponse()
                        else -> {
                            onError(
                                parseGitHubErrorResponse(response.errorBody()?.string())
                                    ?: App.context!!.getString(R.string.wrong_server_response)
                            )
                        }
                    }
                }
            }
        )
    }

    fun updateUser(
        user: UserToPatch,
        onResponse: (GitHubUser) -> Unit,
        onError: (String) -> Unit
    ) {
        Network.gitHubApi.updateUser(user).enqueue(
            object : Callback<GitHubUser> {
                override fun onFailure(call: Call<GitHubUser>, t: Throwable) {
                    onError(App.context!!.getString(R.string.error_internet_connection))
                }

                override fun onResponse(call: Call<GitHubUser>, response: Response<GitHubUser>) {
                    if (response.isSuccessful) {
                        onResponse(response.body()!!)
                    } else {
                        onError(
                            parseGitHubErrorResponse(response.errorBody()?.string())
                                ?: App.context!!.getString(R.string.wrong_server_response)
                        )
                    }
                }

            }
        )
    }

    private fun parseGitHubErrorResponse(response: String?): String? {
        var errorResponse: GitHubErrorResponse? = null
        try {
            errorResponse = adapter.fromJson(response.orEmpty())
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