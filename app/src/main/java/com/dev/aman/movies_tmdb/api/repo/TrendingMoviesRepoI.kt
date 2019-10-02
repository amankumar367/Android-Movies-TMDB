package com.dev.aman.movies_tmdb.api.repo

import com.dev.aman.movies_tmdb.api.data.TrendingMovies
import com.dev.aman.movies_tmdb.api.retrofit.ApiCallback

interface TrendingMoviesRepoI {
    fun getTrendingMovies(apiCallback: ApiCallback<TrendingMovies>)
}