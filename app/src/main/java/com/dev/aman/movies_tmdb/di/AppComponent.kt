package com.dev.aman.movies_tmdb.di

import android.app.Application
import com.dev.aman.movies_tmdb.BaseApplication
import com.dev.aman.movies_tmdb.data.repo.PopularRepo
import com.dev.aman.movies_tmdb.data.repo.TrendingMoviesRepo
import com.dev.aman.movies_tmdb.data.repo.TrendingTVShowsRepo
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
    ViewModelFactoryModule::class])
interface AppComponent : AndroidInjector<BaseApplication> {

    fun inject(trendingMoviesRepo: TrendingMoviesRepo)

    fun inject(trendingTVShowsRepo: TrendingTVShowsRepo)

    fun inject(popularRepo: PopularRepo)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}