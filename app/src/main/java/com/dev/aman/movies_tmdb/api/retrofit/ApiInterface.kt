package com.dev.aman.movies_tmdb.api.retrofit

import com.dev.aman.movies_tmdb.api.data.TrendingMovies
import com.dev.aman.movies_tmdb.api.data.TrendingTVShows
import com.dev.aman.movies_tmdb.utils.ApiConstants
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiInterface {

    @GET(ApiConstants.TRENDING_WEEKS_MOVIES)
    fun getTrendingWeekMovies() : Observable<TrendingMovies>

    @GET(ApiConstants.TRENDING_WEEKS_TV)
    fun getTrendingWeekTVShows() : Observable<TrendingTVShows>


}