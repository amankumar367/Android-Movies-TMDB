package com.dev.aman.movies_tmdb.di

import androidx.lifecycle.ViewModelProvider
import com.dev.aman.movies_tmdb.viewmodel.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}