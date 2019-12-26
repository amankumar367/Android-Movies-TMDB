package com.dev.aman.movies_tmdb.data.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dev.aman.movies_tmdb.data.model.TrendingMovies
import com.dev.aman.movies_tmdb.network.ApiInterface

class DataSourceFactory(
    private val movieApi: ApiInterface)
    : DataSource.Factory<String, TrendingMovies.Result>() {

    private val sourceLiveData = MutableLiveData<ItemKeyedMoviesDataSource>()

    override fun create(): DataSource<String, TrendingMovies.Result> {
        val source = ItemKeyedMoviesDataSource(movieApi)
        sourceLiveData.postValue(source)
        return source
    }
}