package com.dev.aman.movies_tmdb.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dev.aman.movies_tmdb.data.model.NowPlaying
import com.dev.aman.movies_tmdb.data.model.TrendingMovies
import com.dev.aman.movies_tmdb.data.model.UpcomingMovies
import io.reactivex.Single

interface TrendingMoviesRepoI {
    fun getTrendingMovies(): LiveData<PagedList<TrendingMovies.Result>>

    fun getNowPlaying(): Single<NowPlaying>

    fun getUpcomingMovies(): Single<UpcomingMovies>
}