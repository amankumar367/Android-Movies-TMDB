package com.dev.aman.movies_tmdb.data.repo

import com.dev.aman.movies_tmdb.data.model.NowPlaying
import com.dev.aman.movies_tmdb.data.model.TrendingMovies
import com.dev.aman.movies_tmdb.data.model.UpcomingMovies
import com.dev.aman.movies_tmdb.network.ApiCallback
import io.reactivex.Single

interface TrendingMoviesRepoI {
    fun getTrendingMovies(apiCallback: ApiCallback<TrendingMovies>)

    fun getNowPlaying(): Single<NowPlaying>

    fun getUpcomingMovies(): Single<UpcomingMovies>
}