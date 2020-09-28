package com.skillbox.github.model

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.github.utils.SingleLiveEvent
import kotlinx.coroutines.*

object GithubViewModel : ViewModel() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorLiveData.postValue(throwable.message)
    }

    private val repository = GitHubRepository()

    private val currentUserLiveData = MutableLiveData<GitHubUser>()
    val currentUser: LiveData<GitHubUser>
        get() = currentUserLiveData

    private val currentFollowingUsersLiveData = MutableLiveData<List<GitHubUser>>()
    val currentFollowingUsers: LiveData<List<GitHubUser>>
        get() = currentFollowingUsersLiveData

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


    @ExperimentalCoroutinesApi
    fun getCurrentUser() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val defferedUser = viewModelScope.async(coroutineExceptionHandler) {
                repository.getCurrentUser()
            }
            val defferedFollowings = viewModelScope.async(coroutineExceptionHandler) {
                repository.getFollowingUsers()
            }
            defferedUser.await()
            defferedFollowings.await()
            currentUserLiveData.postValue(defferedUser.getCompleted())
            currentFollowingUsersLiveData.postValue(defferedFollowings.getCompleted())

        }


    }

    fun updateUser(user: UserToPatch) {
        viewModelScope.launch(coroutineExceptionHandler) {
            currentUserLiveData.postValue(repository.updateUser(user))
        }
    }

    @ExperimentalCoroutinesApi
    fun setCurrentRepo(position: Int) {
        val selectedRepo = publicReposLiveData.value!![position]
        currentRepoLiveData.postValue(selectedRepo)
        checkIfRepoStarred(selectedRepo)
    }

    fun getPublicRepos() {
        coroutineScope.launch {
            try {
                publicReposLiveData.postValue(repository.getPublicRepos())
            } catch (t: Throwable) {
                publicReposLiveData.postValue(emptyList())
                errorLiveData.postValue(t.message)
            }
        }

    }

    override fun onCleared() {
        coroutineScope.coroutineContext.cancelChildren()
    }


    @ExperimentalCoroutinesApi
    private fun checkIfRepoStarred(repo: GitHubRepo) {
        viewModelScope.launch {
            try {
                repo.starred = repository.checkIfRepoStarred(
                    owner = repo.owner.login,
                    repo = repo.name
                )
                currentRepoLiveData.postValue(repo)
            } catch (t: Throwable) {
                errorLiveData.postValue(t.message)
            }
        }

    }

    @ExperimentalCoroutinesApi
    private fun starRepo(repo: GitHubRepo) {
        viewModelScope.launch {
            try {
                repository.starRepo(
                    owner = repo.owner.login,
                    repo = repo.name
                )
                checkIfRepoStarred(repo)
            } catch (t: Throwable) {
                errorLiveData.postValue(t.message)

            }

        }
    }

    @ExperimentalCoroutinesApi
    private fun unStarRepo(repo: GitHubRepo) {
        viewModelScope.launch {
            try {
                repository.unStarRepo(
                    owner = repo.owner.login,
                    repo = repo.name
                )
                checkIfRepoStarred(repo)
            } catch (t: Throwable) {
                errorLiveData.postValue(t.message)

            }

        }
    }

    @ExperimentalCoroutinesApi
    fun reverseStar() {
        val currentRepo = currentRepoLiveData.value!!
        when (currentRepo.starred) {
            true -> unStarRepo(currentRepo)
            false -> starRepo(currentRepo)
            else -> starRepo(currentRepo)
        }
    }
}