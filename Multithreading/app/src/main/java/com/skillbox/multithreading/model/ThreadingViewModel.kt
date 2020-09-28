package com.skillbox.multithreading.model

import android.app.Application
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skillbox.multithreading.R
import com.skillbox.multithreading.networking.Movie

class ThreadingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MyRepository()

    private var movieIds = listOf(
        "tt0133093",
        "tt0137523",
        "tt0109830",
        "tt0068646",
        "tt0167261"
    )

    private var testValue: Int = 0

    private val moviesLiveData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = moviesLiveData

    private val showRefreshToastLiveData = MutableLiveData<Unit>()
    val showRefreshToast: LiveData<Unit>
        get() = showRefreshToastLiveData

    private val incrementResultLiveData = MutableLiveData<String>()
    val incrementResult: LiveData<String>
        get() = incrementResultLiveData

    fun requestMovies() {
        repository.fetchMovies(movieIds) {
            moviesLiveData.postValue(it)
            Handler().postDelayed({
                showRefreshToastLiveData.postValue(Unit)
            }, 1000)
        }
    }

    fun incrementValue(threadCount: Int, increment: Int, wSync: Boolean) {

        val expectedValue = testValue + (threadCount * increment)
        repository.incrementValue(threadCount, increment, testValue, wSync) { result, time ->
            incrementResultLiveData.postValue(
                getApplication<Application>().resources.getString(
                    R.string.increment_result,
                    expectedValue,
                    result,
                    time
                )
            )
        }

    }

}
