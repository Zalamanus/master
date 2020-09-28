package com.example.networking.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.networking.R
import com.example.networking.model.Movie
import com.example.networking.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*


class MovieViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    private var movie: Movie? = null

    fun bind(movie: Movie?) {
        if (movie == null) {
            val resources = itemView.resources
            movieTitleTV.text = resources.getString(R.string.loading)
            movieYearTV.text = resources.getString(R.string.loading)
            movieTypeTV.text = resources.getString(R.string.loading)
            movieIdTV.text = resources.getString(R.string.loading)
            posterIV.isVisible = false
        } else {
            this.movie = movie
            posterIV.isVisible = true
            Glide.with(itemView)
                .load(movie.poster)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_baseline_movie)
                .error(R.drawable.ic_baseline_error_outline)
                .into(posterIV)

            movieTitleTV.text = movie.title
            movieYearTV.text = movie.year
            movieTypeTV.text = containerView
                .context.resources.getStringArray(R.array.movie_types_array)[movie.type]
            movieIdTV.text = containerView.context.getString(R.string.movieId, movie.id)

        }
    }


    companion object {
        fun create(parent: ViewGroup): MovieViewHolder {
            val view = parent.inflate(R.layout.item_movie)
            return MovieViewHolder(view)
        }
    }
}