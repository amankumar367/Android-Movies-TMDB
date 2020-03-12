package com.dev.aman.movies_tmdb.data.repo.tvshows

import com.dev.aman.movies_tmdb.data.model.Common
import io.reactivex.Single

interface TVShowsRepoI {
    fun getTrendingTVShows(page: Int): Single<Common>
}
