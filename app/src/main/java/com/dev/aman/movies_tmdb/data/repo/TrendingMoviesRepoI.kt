package com.dev.aman.movies_tmdb.data.repo

import com.dev.aman.movies_tmdb.data.model.TrendingMovies
import com.dev.aman.movies_tmdb.network.ApiCallback

interface TrendingMoviesRepoI {
    fun getTrendingMovies(apiCallback: ApiCallback<TrendingMovies>)
}