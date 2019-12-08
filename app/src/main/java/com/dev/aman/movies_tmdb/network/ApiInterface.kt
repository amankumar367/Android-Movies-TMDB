package com.dev.aman.movies_tmdb.network

import com.dev.aman.movies_tmdb.data.model.TrendingMovies
import com.dev.aman.movies_tmdb.data.model.TrendingTVShows
import com.dev.aman.movies_tmdb.utils.ApiConstants
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiInterface {

    @GET(ApiConstants.TRENDING_WEEKS_MOVIES)
    fun getTrendingWeekMovies() : Observable<TrendingMovies>

    @GET(ApiConstants.TRENDING_WEEKS_TV)
    fun getTrendingWeekTVShows() : Observable<TrendingTVShows>


}