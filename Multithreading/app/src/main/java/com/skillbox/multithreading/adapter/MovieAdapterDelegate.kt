package com.skillbox.multithreading.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.multithreading.R
import com.skillbox.multithreading.networking.Movie
import com.skillbox.multithreading.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*

class MovieAdapterDelegate : AbsListItemAdapterDelegate<Movie, Movie, MovieAdapterDelegate.MovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup): MovieHolder {
        return MovieHolder(
            parent.inflate(R.layout.item_movie)
        )
    }



    override fun isForViewType(item: Movie, items: MutableList<Movie>, position: Int): Boolean {
        return true
    }

    override fun onBindViewHolder(
        item: Movie,
        holder: MovieHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class MovieHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(movie: Movie) {
            movieTitleTV.text = movie.title
            movieYearTV.text = movie.year.toString()
        }
    }

}
