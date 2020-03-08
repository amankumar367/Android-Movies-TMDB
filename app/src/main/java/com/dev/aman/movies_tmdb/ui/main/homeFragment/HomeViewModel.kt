package com.dev.aman.movies_tmdb.ui.main.homeFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dev.aman.movies_tmdb.data.model.Result
import com.dev.aman.movies_tmdb.data.repo.*
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

class HomeViewModel(
    private val moviesRepoI: MoviesRepoI,
    private val tvShowsRepoI: TVShowsRepoI,
    private val popularRepoI: PopularRepoI
): ViewModel() {

    lateinit var trendingMoviesPageList: LiveData<PagedList<Result>>
    lateinit var trendingTVShowsPageList: LiveData<PagedList<Result>>
    lateinit var nowPlayingPageList: LiveData<PagedList<Result>>
    lateinit var upcomingMoviesPageList: LiveData<PagedList<Result>>
    lateinit var popularPeoplePageList: LiveData<PagedList<Result>>

    lateinit var trendingMoviesStateObservable: LiveData<HomeState>
    lateinit var trendingTVShowsStateObservable: LiveData<HomeState>
    lateinit var nowPlayingStateObservable: LiveData<HomeState>
    lateinit var upcomingMoviesStateObservable: LiveData<HomeState>
    lateinit var popularPeopleStateObservable: LiveData<HomeState>

    private var compositeDisposable = CompositeDisposable()

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(10)
        .build()

    fun getTrendingMovies() {
        Log.d(TAG, " >>> Trying to get trending movies")

        val sourceFactory = DataSourceFactory(moviesRepoI, tvShowsRepoI, popularRepoI,
            compositeDisposable, RequestType.TRENDING_MOVIE)

        trendingMoviesPageList = LivePagedListBuilder(sourceFactory, config).build()

        trendingMoviesStateObservable = Transformations.switchMap<TrendingMoviesDataSource, HomeState>(
            sourceFactory.trendingMoviesLiveData, TrendingMoviesDataSource::stateObservable)
    }

    fun getTrendingTVShows() {
        Log.d(TAG, " >>> Trying to get trending TV shows")

        val sourceFactory = DataSourceFactory(moviesRepoI, tvShowsRepoI, popularRepoI,
            compositeDisposable, RequestType.TRENDING_TVSHOWS)

        trendingTVShowsPageList = LivePagedListBuilder(sourceFactory, config).build()

        trendingTVShowsStateObservable = Transformations.switchMap<TrendingTVShowsDataSource, HomeState>(
            sourceFactory.trendingTVShowsLiveData, TrendingTVShowsDataSource::stateObservable)
    }

    fun getNowPlaying() {
        Log.d(TAG, " >>> Trying to get now playing movies")

        val sourceFactory = DataSourceFactory(moviesRepoI, tvShowsRepoI, popularRepoI,
            compositeDisposable, RequestType.NOW_PLAYING)

        nowPlayingPageList = LivePagedListBuilder(sourceFactory, config).build()

        nowPlayingStateObservable = Transformations.switchMap<NowPlayingDataSource, HomeState>(
            sourceFactory.nowPlayingLiveData, NowPlayingDataSource::stateObservable)
    }

    fun getUpcomingMovies() {
        Log.d(TAG, " >>> Trying to get upcoming movies")

        val sourceFactory = DataSourceFactory(moviesRepoI, tvShowsRepoI, popularRepoI,
            compositeDisposable, RequestType.UPCOMING_MOVIES)

        upcomingMoviesPageList = LivePagedListBuilder(sourceFactory, config).build()

        upcomingMoviesStateObservable = Transformations.switchMap<UpcomingDataSource, HomeState>(
            sourceFactory.upcomingMoviesLiveData, UpcomingDataSource::stateObservable)
    }

    fun getPopularPeoples() {
        Log.d(TAG, " >>> Trying to get popular peoples")

        val sourceFactory = DataSourceFactory(moviesRepoI, tvShowsRepoI, popularRepoI,
            compositeDisposable, RequestType.POPULAR_PEOPLES)

        popularPeoplePageList = LivePagedListBuilder(sourceFactory, config).build()

        popularPeopleStateObservable = Transformations.switchMap<PopularDataSource, HomeState>(
            sourceFactory.popularPeopleLiveData, PopularDataSource::stateObservable)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, " >>> Clearing compositeDisposable object")
        compositeDisposable.dispose()
    }

    companion object {
        const val TAG = "HomeViewModel"
    }

}