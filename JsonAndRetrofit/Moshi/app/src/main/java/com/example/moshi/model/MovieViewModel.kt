package com.example.moshi.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository()

    private lateinit var lastKeyWords: String

    private val movieLiveData = MutableLiveData<Movie?>()
    val movie: LiveData<Movie?>
        get() = movieLiveData

    private val errorLiveData = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = errorLiveData

    fun searchMovie(keyWords: String) {
        lastKeyWords = keyWords
        repository.fetchMovie(
            keyWords,
            {
                movieLiveData.postValue(it)
                errorLiveData.postValue(null)
            },
            {
                movieLiveData.postValue(null)
                errorLiveData.postValue(it)
            })

    }

    fun repeatSearch() {
        searchMovie(lastKeyWords)
    }

    fun setUserScore(score: Int) {
        val tempMovie = movie.value!!
        tempMovie.scores["userScore"] = score.toString()
        movieLiveData.postValue(tempMovie)
        repository.saveMovie(tempMovie)
    }
}