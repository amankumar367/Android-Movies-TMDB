package com.dev.aman.movies_tmdb.data.repo

import com.dev.aman.movies_tmdb.data.model.TrendingTVShows
import com.dev.aman.movies_tmdb.network.ApiCallback

interface TrendingTVShowsRepoI {
    fun getTrendingTVShows(apiCallback: ApiCallback<TrendingTVShows>)
}
