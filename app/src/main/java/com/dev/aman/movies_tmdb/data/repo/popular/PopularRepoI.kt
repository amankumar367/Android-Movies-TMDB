package com.dev.aman.movies_tmdb.data.repo.popular

import com.dev.aman.movies_tmdb.data.model.Common
import io.reactivex.Single

interface PopularRepoI {
    fun getPopularPeople(page: Int): Single<Common>
}
