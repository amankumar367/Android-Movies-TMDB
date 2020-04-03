package com.dev.aman.movies_tmdb.di

import com.dev.aman.movies_tmdb.di.main.MainFragmentBuilderModule
import com.dev.aman.movies_tmdb.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainFragmentBuilderModule::class])
    abstract fun mainActivityProvider() : MainActivity

}