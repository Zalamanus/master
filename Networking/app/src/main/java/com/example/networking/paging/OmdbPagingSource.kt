package com.example.networking.paging

import androidx.paging.PagingSource
import com.example.networking.model.Movie
import com.example.networking.network.Network

private const val OMDB_STARTING_PAGE_INDEX = 1

class OmdbPagingSource(private val keyWords: String,
                       private val year: String,
                       private val movieType: String) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: OMDB_STARTING_PAGE_INDEX
        return try {
            val response = Network.searchMovie(keyWords, year, movieType, position)
            val movies = response.items
            LoadResult.Page(
                data = movies,
                prevKey = if (position == OMDB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}