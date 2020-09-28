package com.skillbox.multithreading.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.multithreading.networking.Movie

class MovieAdapter: AsyncListDifferDelegationAdapter<Movie>(MovieDiffUtilCallback()) {

    init {
        delegatesManager
            .addDelegate(MovieAdapterDelegate())

    }

    class MovieDiffUtilCallback: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
}