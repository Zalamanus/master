package com.example.networking.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.networking.paging.OmdbPagingSource
import kotlinx.coroutines.flow.Flow

class MovieRepository {

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }


    fun getSearchResultStream(
        keyWords: String,
        year: String,
        movieType: String
    ): Flow<PagingData<Movie>> {
        return Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 50,
                enablePlaceholders = true)
        ) {
            OmdbPagingSource(keyWords,year,movieType)
        }.flow
    }




}