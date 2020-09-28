package com.example.moshi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.moshi.model.Movie
import com.example.moshi.model.MovieViewModel
import com.example.moshi.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_movie.*
import kotlinx.android.synthetic.main.item_score.view.*

class MainActivity : AppCompatActivity() {
    private val viewModel: MovieViewModel by viewModels()

    private val scoreViews: MutableList<View> = mutableListOf()

    private fun search(keyWords: String) {
        updateLoadingState(true)
        viewModel.searchMovie(keyWords)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListeners()

        setObservers()

    }

    private fun setObservers() {
        viewModel.movie.observe(this) { movie ->
            updateLoadingState(false)
            movie?.also {
                setMovieToUI(it)
                showMovie(true)
            }
        }
        viewModel.error.observe(this) { errorString ->
            errorString?.also {
                onError(it)
                showMovie(false)
            }
        }
    }

    private fun setListeners() {
        search_button.setOnClickListener {
            val keyWords = movieNameET.text.trim().toString()
            if (keyWords.isEmpty()) {
                showToast(
                    getString(R.string.keywors_is_empty),
                    this
                )
            } else {
                search(keyWords)
            }
        }
        repeatButton.setOnClickListener {
            viewModel.repeatSearch()
        }
        star1.setOnClickListener { viewModel.setUserScore(1) }
        star2.setOnClickListener { viewModel.setUserScore(2) }
        star3.setOnClickListener { viewModel.setUserScore(3) }
        star4.setOnClickListener { viewModel.setUserScore(4) }
        star5.setOnClickListener { viewModel.setUserScore(5) }
    }

    private fun setMovieToUI(movie: Movie) {

        Glide.with(mainContainer)
            .load(movie.poster)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_baseline_movie)
            .error(R.drawable.ic_baseline_error_outline)
            .into(posterIV)

        movieTitleTV.text = movie.title
        movieGenreTV.text = movie.genre
        movieYearTV.text = movie.year
        movieRateTV.text = movie.rate.desc
        movieIdTV.text = getString(R.string.movieId, movie.id)

        //reset movie scores
        scoreViews.forEach {
            movieDataContainer.removeView(it)
        }
        setStars(0)

        movie.scores.map {
            if (it.key != "userScore") {
                val view = View.inflate(baseContext, R.layout.item_score, null)
                view.scoreTV.text = getString(R.string.score_pattent, it.key, it.value)
                movieDataContainer.addView(view)
                scoreViews.add(view)
                scoresTitleTV.isVisible = true
            } else {
                setStars(it.value.toInt())
            }
        }

    }

    private fun setStars(score: Int) {
        when (score) {
            1 -> {
                star1.setImageResource(R.drawable.ic_baseline_star_full)
                star2.setImageResource(R.drawable.ic_baseline_star_hollow)
                star3.setImageResource(R.drawable.ic_baseline_star_hollow)
                star4.setImageResource(R.drawable.ic_baseline_star_hollow)
                star5.setImageResource(R.drawable.ic_baseline_star_hollow)
            }
            2 -> {
                star1.setImageResource(R.drawable.ic_baseline_star_full)
                star2.setImageResource(R.drawable.ic_baseline_star_full)
                star3.setImageResource(R.drawable.ic_baseline_star_hollow)
                star4.setImageResource(R.drawable.ic_baseline_star_hollow)
                star5.setImageResource(R.drawable.ic_baseline_star_hollow)
            }
            3 -> {
                star1.setImageResource(R.drawable.ic_baseline_star_full)
                star2.setImageResource(R.drawable.ic_baseline_star_full)
                star3.setImageResource(R.drawable.ic_baseline_star_full)
                star4.setImageResource(R.drawable.ic_baseline_star_hollow)
                star5.setImageResource(R.drawable.ic_baseline_star_hollow)
            }
            4 -> {
                star1.setImageResource(R.drawable.ic_baseline_star_full)
                star2.setImageResource(R.drawable.ic_baseline_star_full)
                star3.setImageResource(R.drawable.ic_baseline_star_full)
                star4.setImageResource(R.drawable.ic_baseline_star_full)
                star5.setImageResource(R.drawable.ic_baseline_star_hollow)
            }
            5 -> {
                star1.setImageResource(R.drawable.ic_baseline_star_full)
                star2.setImageResource(R.drawable.ic_baseline_star_full)
                star3.setImageResource(R.drawable.ic_baseline_star_full)
                star4.setImageResource(R.drawable.ic_baseline_star_full)
                star5.setImageResource(R.drawable.ic_baseline_star_full)
            }
            else -> {
                star1.setImageResource(R.drawable.ic_baseline_star_hollow)
                star2.setImageResource(R.drawable.ic_baseline_star_hollow)
                star3.setImageResource(R.drawable.ic_baseline_star_hollow)
                star4.setImageResource(R.drawable.ic_baseline_star_hollow)
                star5.setImageResource(R.drawable.ic_baseline_star_hollow)
            }

        }
    }

    private fun onError(errorString: String) {
        errorTextTV.text = errorString
        errorTextTV.isVisible = true
        repeatButton.isVisible = true
//        movieContainer.isVisible = false
    }

    private fun updateLoadingState(isLoading: Boolean) {
//        movieContainer.isVisible = isLoading.not()
        progressBar.isVisible = isLoading
        movieNameET.isEnabled = isLoading.not()
        search_button.isEnabled = isLoading.not()
    }

    private fun showMovie(needToShow: Boolean) {
        movieContainer.isVisible = needToShow
        repeatButton.isVisible = needToShow.not()
        errorTextTV.isVisible = needToShow.not()

    }
}

