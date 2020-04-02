package com.dev.aman.movies_tmdb.di.main

import com.dev.aman.movies_tmdb.ui.main.homeFragment.HomeFragment
import com.dev.aman.movies_tmdb.ui.main.progressFragment.ProgressFragment
import com.dev.aman.movies_tmdb.ui.main.trailerFragment.TrailerFragment
import com.dev.aman.movies_tmdb.ui.movies.MoviesFragment
import com.dev.aman.movies_tmdb.ui.popularpeople.PopularPeopleFragment
import com.dev.aman.movies_tmdb.ui.tvshows.TVShowsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun mainFragmentProvider() : HomeFragment

    @ContributesAndroidInjector
    abstract fun progressFragmentProvider() : ProgressFragment

    @ContributesAndroidInjector
    abstract fun trailerFragmentProvider() : TrailerFragment

    @ContributesAndroidInjector
    abstract fun moviesFragmentProvider() : MoviesFragment

    @ContributesAndroidInjector
    abstract fun popularPeopleFragmentProvider() : PopularPeopleFragment

    @ContributesAndroidInjector
    abstract fun tvShowsFragmentProvider() : TVShowsFragment
}