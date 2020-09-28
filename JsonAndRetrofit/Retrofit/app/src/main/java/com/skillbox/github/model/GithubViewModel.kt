package com.skillbox.github.model

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.utils.SingleLiveEvent

object GithubViewModel : ViewModel() {

    private val repository = GitHubRepository()

    private val currentUserLiveData = MutableLiveData<GitHubUser>()
    val currentUser: LiveData<GitHubUser>
        get() = currentUserLiveData

    private val currentRepoLiveData = MutableLiveData<GitHubRepo>()
    val currentRepo: LiveData<GitHubRepo>
        get() = currentRepoLiveData

    private val errorLiveData = SingleLiveEvent<String>()
    val error: LiveData<String>
        get() = errorLiveData

    private val publicReposLiveData = MutableLiveData<List<GitHubRepo>>()
    val publicRepos: LiveData<List<GitHubRepo>>
        get() = publicReposLiveData

    private var toolbarTitleLiveData = MutableLiveData<Int>()
    val toolbarTitle: LiveData<Int>
        get() = toolbarTitleLiveData

    @StringRes
    var currentUserButtonModeRes: Int? = null

    fun setToolbarTitle(@StringRes title: Int) {
        toolbarTitleLiveData.postValue(title)
    }


    fun getCurrentUser() {
        repository.getCurrentUser(
            onResponse = {
                currentUserLiveData.postValue(it)
            },
            onError = {
                errorLiveData.postValue(it)
            })


    }

    fun updateUser(user: UserToPatch) {
        repository.updateUser(
            user = user,
            onResponse = {
                currentUserLiveData.postValue(it)
            },
            onError = {
                errorLiveData.postValue(it)
            })


    }

    fun setCurrentRepo(position: Int) {
        currentRepoLiveData.postValue(publicReposLiveData.value!![position])
    }

    fun getPublicRepos() {
        repository.getPublicRepos(
            onResponse = {
                publicReposLiveData.postValue(it)
            },
            onError = {
                errorLiveData.postValue(it)
            })

    }

    private fun checkIfRepoStarred(repo: GitHubRepo) {
        repository.checkIfRepoStarred(
            owner = repo.owner.login,
            repo = repo.name,
            onResponse = {
                currentRepoLiveData.value!!.starred = it
                currentRepoLiveData.postValue(currentRepoLiveData.value!!)
            },
            onError = {
                errorLiveData.postValue(it)
            })
    }

    private fun starRepo(repo: GitHubRepo) {
        repository.starRepo(
            owner = repo.owner.login,
            repo = repo.name,
            onResponse = {
                checkIfRepoStarred(repo)
            },
            onError = {
                errorLiveData.postValue(it)
            })
    }

    private fun unStarRepo(repo: GitHubRepo) {
        repository.unStarRepo(
            owner = repo.owner.login,
            repo = repo.name,
            onResponse = {
                checkIfRepoStarred(repo)
            },
            onError = {
                errorLiveData.postValue(it)
            })
    }

    fun reverseStar() {
        val currentRepo = currentRepoLiveData.value!!
        when (currentRepo.starred) {
            true -> unStarRepo(currentRepo)
            false -> starRepo(currentRepo)
            else -> starRepo(currentRepo)
        }
    }
}