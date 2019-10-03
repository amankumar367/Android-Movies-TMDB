package com.dev.aman.movies_tmdb.utils

import com.dev.aman.movies_tmdb.BuildConfig

object ApiConstants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_URL_IMAGE = "http://image.tmdb.org/t/p/w185"

    // Trending API URL
    const val TRENDING_WEEKS_ALL = "trending/all/week?api_key=${BuildConfig.TMDB_API_KEY}"
    const val TRENDING_WEEKS_MOVIES = "trending/movie/week?api_key=${BuildConfig.TMDB_API_KEY}"
    const val TRENDING_WEEKS_TV = "trending/tv/week?api_key=${BuildConfig.TMDB_API_KEY}"
    const val TRENDING_WEEKS_PERSON = "trending/person/week?api_key=${BuildConfig.TMDB_API_KEY}"

    const val TRENDING_DAY_ALL = "trending/all/day?api_key=${BuildConfig.TMDB_API_KEY}"
    const val TRENDING_DAY_MOVIES = "trending/movie/day?api_key=${BuildConfig.TMDB_API_KEY}"
    const val TRENDING_DAY_TV = "trending/tv/day?api_key=${BuildConfig.TMDB_API_KEY}"
    const val TRENDING_DAY_PERSON = "trending/person/day?api_key=${BuildConfig.TMDB_API_KEY}"
}