package com.skillbox.multithreading.model

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.skillbox.multithreading.networking.Movie
import com.skillbox.multithreading.networking.Network
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class MyRepository {

    private val backgroundThread = HandlerThread("background thread").apply {
        start()
    }
    private val backgroundHandler = Handler(backgroundThread.looper)

    private fun getMovieById(movieId: String): Movie? {
        return Network.getMovieById(movieId)
    }

    fun fetchMovies(
        movieIds: List<String>,
        onMoviesFetched: (movies: List<Movie>) -> Unit
    ) {
        val coresCount = Runtime.getRuntime().availableProcessors()
        val threadPoolExecutor = ThreadPoolExecutor(
            coresCount, coresCount, 0,
            TimeUnit.MILLISECONDS, LinkedBlockingQueue<Runnable>()
        )
        val movies = Collections.synchronizedList(mutableListOf<Movie>())


        backgroundHandler.post {
            movieIds.forEach {
                threadPoolExecutor.execute {
                    movies.add(getMovieById(it))
                }
            }
            threadPoolExecutor.shutdown()
            threadPoolExecutor.awaitTermination(2, TimeUnit.SECONDS)

            Handler(Looper.getMainLooper()).post {
                onMoviesFetched(movies)
            }

        }


    }

    fun incrementValue(
        threadCount: Int, increment: Int, testValue: Int, wSync: Boolean,
        onResultCalculated: (result: Int, time: Long) -> Unit
    ) {
        var resultValue = testValue
        val threadPoolExecutor = ThreadPoolExecutor(
            threadCount, threadCount, 0,
            TimeUnit.MILLISECONDS, LinkedBlockingQueue<Runnable>()
        )
        backgroundHandler.post {
            val startTime = System.currentTimeMillis()
            (0 until threadCount).forEach { _ ->
                when (wSync) {
                    false -> {
                        threadPoolExecutor.execute {
                            (0 until increment).forEach { _ ->
                                resultValue++
                            }
                        }

                    }
                    true -> {
                        threadPoolExecutor.execute {
                            synchronized(this) {
                                (0 until increment).forEach { _ ->
                                    resultValue++
                                }
                            }
                        }

                    }
                }
            }
            threadPoolExecutor.shutdown()
            threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)
            val stopTime = System.currentTimeMillis()
            val incrementTime = stopTime - startTime
            onResultCalculated(resultValue, incrementTime)
        }

    }
}
