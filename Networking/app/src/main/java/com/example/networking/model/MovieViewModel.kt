package com.example.networking.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository()

    private lateinit var lastKeyWords: String
    private lateinit var lastYear: String
    private lateinit var lastMovieType: String

    var flow: Flow<PagingData<Movie>>? = null
        private set


    fun searchMovie(keyWords: String, year: String, movieType: String): Flow<PagingData<Movie>> {
        lastKeyWords = keyWords
        lastYear = year
        lastMovieType = movieType
        flow = repository.getSearchResultStream(keyWords,year,movieType)
            .cachedIn(viewModelScope)
        return flow!!
    }

    fun repeatSearch() {
        searchMovie(lastKeyWords, lastYear, lastMovieType)
    }
}