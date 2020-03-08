package com.dev.aman.movies_tmdb.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.dev.aman.movies_tmdb.data.model.Result
import com.dev.aman.movies_tmdb.data.repo.movies.MoviesRepoI
import com.dev.aman.movies_tmdb.data.repo.movies.datasource.NowPlayingDataSource
import com.dev.aman.movies_tmdb.data.repo.movies.datasource.TrendingMoviesDataSource
import com.dev.aman.movies_tmdb.data.repo.movies.datasource.UpcomingDataSource
import com.dev.aman.movies_tmdb.data.repo.popular.PopularRepoI
import com.dev.aman.movies_tmdb.data.repo.popular.datasource.PopularDataSource
import com.dev.aman.movies_tmdb.data.repo.tvshows.TVShowsRepoI
import com.dev.aman.movies_tmdb.data.repo.tvshows.datasource.TrendingTVShowsDataSource
import com.dev.aman.movies_tmdb.network.RequestType
import io.reactivex.disposables.CompositeDisposable

class DataSourceFactory(
    private val moviesRepoI: MoviesRepoI,
    private val tvShowsRepoI: TVShowsRepoI,
    private val popularRepoI: PopularRepoI,
    private val compositeDisposable: CompositeDisposable,
    private val requestType: RequestType
) : DataSource.Factory<Int, Result>() {

    val trendingMoviesLiveData = MutableLiveData<TrendingMoviesDataSource>()
    val trendingTVShowsLiveData = MutableLiveData<TrendingTVShowsDataSource>()
    val nowPlayingLiveData = MutableLiveData<NowPlayingDataSource>()
    val upcomingMoviesLiveData = MutableLiveData<UpcomingDataSource>()
    val popularPeopleLiveData = MutableLiveData<PopularDataSource>()
    val topPicksLiveData = MutableLiveData<PopularDataSource>()

    override fun create(): DataSource<Int, Result> {
        Log.d(TAG, " >>> Creating data source factory for : $requestType")

        val source: PageKeyedDataSource<Int, Result>

        when (requestType) {
            RequestType.TRENDING_MOVIE -> {
                source = TrendingMoviesDataSource(moviesRepoI, compositeDisposable, requestType)
                trendingMoviesLiveData.postValue(source)
            }
            RequestType.TRENDING_TVSHOWS -> {
                source = TrendingTVShowsDataSource(tvShowsRepoI, compositeDisposable, requestType)
                trendingTVShowsLiveData.postValue(source)
            }
            RequestType.NOW_PLAYING -> {
                source = NowPlayingDataSource(moviesRepoI, compositeDisposable, requestType)
                nowPlayingLiveData.postValue(source)
            }
            RequestType.UPCOMING_MOVIES -> {
                source = UpcomingDataSource(moviesRepoI, compositeDisposable, requestType)
                upcomingMoviesLiveData.postValue(source)
            }
            RequestType.POPULAR_PEOPLES -> {
                source = PopularDataSource(popularRepoI, compositeDisposable, requestType)
                popularPeopleLiveData.postValue(source)
            }
            RequestType.TOP_PICKS -> {
                source = PopularDataSource(popularRepoI, compositeDisposable, requestType)
                topPicksLiveData.postValue(source)
            }
        }
        return source
    }

    companion object {
        const val TAG = "DataSourceFactory"
    }
}