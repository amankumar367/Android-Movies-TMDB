package com.dev.aman.movies_tmdb.di

import android.app.Application
import com.dev.aman.movies_tmdb.BaseApplication
import com.dev.aman.movies_tmdb.api.repo.TrendingMoviesRepo
import com.dev.aman.movies_tmdb.api.repo.TrendingTVShowsRepo
import com.dev.aman.movies_tmdb.di.main.MainFragmentBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    NetworkModule::class,
    ActivityBuilderModule::class,
    MainFragmentBuilderModule::class,
    ViewModelFactoryModule::class])
interface AppComponent : AndroidInjector<BaseApplication> {

    fun inject(trendingMoviesRepo: TrendingMoviesRepo)

    fun inject(trendingTVShowsRepo: TrendingTVShowsRepo)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}