package com.dev.aman.movies_tmdb.api.repo

import com.dev.aman.movies_tmdb.api.data.TrendingTVShows
import com.dev.aman.movies_tmdb.api.retrofit.ApiCallback

interface TrendingTVShowsRepoI {
    fun getTrendingTVShows(apiCallback: ApiCallback<TrendingTVShows>)
}
