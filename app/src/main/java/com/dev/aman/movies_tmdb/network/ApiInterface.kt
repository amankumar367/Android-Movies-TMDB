package com.dev.aman.movies_tmdb.network

import com.dev.aman.movies_tmdb.data.model.*
import com.dev.aman.movies_tmdb.utils.ApiConstants
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(ApiConstants.TRENDING_WEEKS_MOVIES)
    fun getTrendingWeekMovies(@Query("page") page: Int) : Call<TrendingMovies>

    @GET(ApiConstants.TRENDING_WEEKS_TV)
    fun getTrendingWeekTVShows() : Observable<TrendingTVShows>

    @GET(ApiConstants.NOW_PLAYING)
    fun getNowPlaying(): Call<NowPlaying>

    @GET(ApiConstants.UPCOMING_MPVIES)
    fun getUpcomingMovies(): Call<UpcomingMovies>

    @GET(ApiConstants.POPULAR_PEOPLE)
    fun getPopularPeople(): Call<PopularPeople>

}