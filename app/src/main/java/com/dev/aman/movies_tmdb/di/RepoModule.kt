package com.dev.aman.movies_tmdb.di

import com.dev.aman.movies_tmdb.data.repo.movies.MoviesRepo
import com.dev.aman.movies_tmdb.data.repo.movies.MoviesRepoI
import com.dev.aman.movies_tmdb.data.repo.popular.PopularRepo
import com.dev.aman.movies_tmdb.data.repo.popular.PopularRepoI
import com.dev.aman.movies_tmdb.data.repo.tvshows.TVShowsRepo
import com.dev.aman.movies_tmdb.data.repo.tvshows.TVShowsRepoI
import com.dev.aman.movies_tmdb.network.ApiInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun provideMoviesRepo(apiInterface: ApiInterface): MoviesRepoI {
        return MoviesRepo(apiInterface)
    }

    @Provides
    @Singleton
    fun provideTVShowsRepo(apiInterface: ApiInterface): TVShowsRepoI {
        return TVShowsRepo(
            apiInterface
        )
    }

    @Provides
    @Singleton
    fun providePopularRepo(apiInterface: ApiInterface): PopularRepoI {
        return PopularRepo(
            apiInterface
        )
    }
}