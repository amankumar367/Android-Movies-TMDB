package com.dev.aman.movies_tmdb.di.main

import com.dev.aman.movies_tmdb.ui.MainFragment
import com.dev.aman.movies_tmdb.ui.ProgressFragment
import com.dev.aman.movies_tmdb.ui.TrailerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun mainFragmentProvider() : MainFragment

    @ContributesAndroidInjector
    abstract fun progressFragmentProvider() : ProgressFragment

    @ContributesAndroidInjector
    abstract fun trailerFragmentProvider() : TrailerFragment
}