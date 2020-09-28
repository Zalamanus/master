package com.skillbox.multithreading.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.multithreading.adapter.MovieAdapter
import com.skillbox.multithreading.R
import com.skillbox.multithreading.model.ThreadingViewModel
import com.skillbox.multithreading.utils.AutoClearedValue
import com.skillbox.multithreading.utils.showToast
import kotlinx.android.synthetic.main.fragment_threading.*

class ThreadingFragment : Fragment(R.layout.fragment_threading) {
    private val viewModel: ThreadingViewModel by viewModels()
    private var movieAdapter by AutoClearedValue<MovieAdapter>()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.requestMovies()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }


    private fun initList() {
        movieAdapter = MovieAdapter()
        viewModel.movies.observe(viewLifecycleOwner) {
            movieAdapter.items = it
        }
        viewModel.showRefreshToast.observe(viewLifecycleOwner) {
            swipeRefreshLayout.isRefreshing = false
            showToast(
                getString(R.string.list_refreshed),
                requireContext()
            )
        }
        with(movieList) {
            adapter = movieAdapter
            val linearLayoutManager = LinearLayoutManager(this.context)
            layoutManager = linearLayoutManager

        }
        viewModel.requestMovies()
    }


}