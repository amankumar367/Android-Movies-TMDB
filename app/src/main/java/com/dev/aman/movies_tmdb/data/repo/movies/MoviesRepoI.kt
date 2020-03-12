package com.dev.aman.movies_tmdb.data.repo.movies

import com.dev.aman.movies_tmdb.data.model.Common
import io.reactivex.Single

interface MoviesRepoI {
    fun getTrendingMovies(page: Int): Single<Common>

    fun getNowPlaying(page: Int): Single<Common>

    fun getUpcomingMovies(page: Int): Single<Common>
}