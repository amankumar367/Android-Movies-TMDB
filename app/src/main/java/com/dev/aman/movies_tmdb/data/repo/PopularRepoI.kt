package com.dev.aman.movies_tmdb.data.repo

import com.dev.aman.movies_tmdb.data.model.PopularPeople
import io.reactivex.Single

interface PopularRepoI {
    fun getPopularPeople(): Single<PopularPeople>
}
