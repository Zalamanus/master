package com.example.networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networking.adapter.MovieAdapter
import com.example.networking.model.MovieViewModel
import com.example.networking.utils.AutoClearedValue
import com.example.networking.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private var movieAdapter by AutoClearedValue<MovieAdapter>()

    private var movieType = 0

    private var searchJob: Job? = null

    private val viewModel by viewModels<MovieViewModel>()


    private fun search(keyWords: String, year: String, movieType: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        // Activities can use lifecycleScope directly, but Fragments should instead use
        // viewLifecycleOwner.lifecycleScope.
        searchJob = lifecycleScope.launch {
            viewModel.searchMovie(keyWords, year, movieType).collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dropDownInit()

        search_button.setOnClickListener {
            val year = movieYearET.text.trim()
            if (movieNameET.text.trim().isEmpty()) {
                showToast(
                    getString(R.string.keywors_is_empty),
                    this
                )
            } else if (!year.matches("^\\d{4}".toRegex()) && year.isNotEmpty()) {
                showToast(
                    getString(R.string.year_isnt_correct),
                    this
                )

            } else {
                search(
                    movieNameET.text.toString(),
                    movieYearET.text.toString(),
                    when (movieType) {
                        1 -> "series"
                        2 -> "movie"
                        3 -> "episode"
                        4 -> "game"
                        else -> ""
                    }
                )
            }
        }
        repeatButton.setOnClickListener {
            viewModel.repeatSearch()
        }
        initList()
    }

    override fun onResume() {
        super.onResume()
        //restore state after rotation
        lifecycleScope.launch {
            viewModel.flow?.collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }

    private fun dropDownInit() {
        val movieTypes = resources.getStringArray(R.array.movie_types_array)
        val adapter = ArrayAdapter(
            applicationContext,
            R.layout.item_movietype_dropdown,
            movieTypes
        )
        val dropDownList = (movietype_dropdown.editText as? AutoCompleteTextView)
        dropDownList?.setAdapter(adapter)
        dropDownList?.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                movieType = position
            }
    }

    private fun initList() {
        movieAdapter = MovieAdapter()
        with(movieList) {
            adapter = movieAdapter
            val linearLayoutManager = LinearLayoutManager(this.context)
            layoutManager = linearLayoutManager

        }
        lifecycleScope.launch {
            movieAdapter.loadStateFlow.collectLatest { loadStates ->
                updateLoadingState(loadStates.refresh is LoadState.Loading)
                if (loadStates.refresh is LoadState.Error) {
                    val loadError = (loadStates.refresh as LoadState.Error).error.message ?: "Error"
                    onError(loadError)
                }
            }
        }
    }

    private fun onError(errorString: String) {
        errorTextTV.text = errorString
        errorTextTV.isVisible = true
        repeatButton.isVisible = true
        movieList.isVisible = false
    }

    private fun updateLoadingState(isLoading: Boolean) {
        errorTextTV.isVisible = false
        repeatButton.isVisible = false
        movieList.isVisible = isLoading.not()
        progressBar.isVisible = isLoading
        movieNameET.isEnabled = isLoading.not()
        movieYearET.isEnabled = isLoading.not()
        movietype_dropdown.isEnabled = isLoading.not()
        search_button.isEnabled = isLoading.not()
    }
}

