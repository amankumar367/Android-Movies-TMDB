package com.dev.aman.movies_tmdb.ui.main.homeFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.aman.movies_tmdb.data.model.TrendingMovies
import com.dev.aman.movies_tmdb.data.model.TrendingTVShows
import com.dev.aman.movies_tmdb.data.repo.TrendingMoviesRepoI
import com.dev.aman.movies_tmdb.data.repo.TrendingTVShowsRepoI
import com.dev.aman.movies_tmdb.network.ApiCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel: ViewModel() {

    var stateObservable: MutableLiveData<HomeState> = MutableLiveData()

    private var compositeDisposableNowPlaying = CompositeDisposable()
    private var compositeDisposableUpcomingMovies = CompositeDisposable()

    private lateinit var trendingMoviesRepoI: TrendingMoviesRepoI
    private lateinit var trendingTVShowsRepoI: TrendingTVShowsRepoI

    var state = HomeState()
        set(value) {
            field = value
            publishState(value)
        }

    fun setRepository(trendingMoviesRepoI: TrendingMoviesRepoI,
                      trendingTVShowsRepoI: TrendingTVShowsRepoI) {
        Log.d(TAG, " >>> Setting up Repository")

        this.trendingMoviesRepoI = trendingMoviesRepoI
        this.trendingTVShowsRepoI = trendingTVShowsRepoI
    }

    fun getTrendingMovies() {
        Log.d(TAG, " >>> Trying to get trending movies")

        state = state.copy(loading = true, eventType = HomeState.EventType.TRENDING_MOVIE)
        trendingMoviesRepoI.getTrendingMovies(object : ApiCallback<TrendingMovies> {
            override fun onSuccess(t: TrendingMovies) {
                state = state.copy(
                    loading = false,
                    success = true,
                    failure = false,
                    data = t,
                    eventType = HomeState.EventType.TRENDING_MOVIE
                )
            }

            override fun onFailure(message: String) {
                state =
                    state.copy(
                        loading = false,
                        success = false,
                        failure = true,
                        message = message,
                        eventType = HomeState.EventType.TRENDING_MOVIE
                    )
            }
        })
    }

    fun getTrendingTVShows() {
        Log.d(TAG, " >>> Trying to get trending TV shows")

        state = state.copy(loading = true, eventType = HomeState.EventType.TRENDING_TVSHOWS)
        trendingTVShowsRepoI.getTrendingTVShows(object : ApiCallback<TrendingTVShows> {
            override fun onSuccess(t: TrendingTVShows) {
                state = state.copy(
                    loading = false,
                    success = true,
                    failure = false,
                    data = t,
                    eventType = HomeState.EventType.TRENDING_TVSHOWS
                )
            }

            override fun onFailure(message: String) {
                state =
                    state.copy(
                        loading = false,
                        success = false,
                        failure = true,
                        message = message,
                        eventType = HomeState.EventType.TRENDING_TVSHOWS
                    )
            }
        })
    }

    fun getNowPlaying() {
        Log.d(TAG, " >>> Trying to get now playing movies")

        state = state.copy(loading = true, eventType = HomeState.EventType.NOW_PLAYING)
        compositeDisposableNowPlaying.add(
            trendingMoviesRepoI.getNowPlaying()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    state =
                        state.copy(
                            loading = false,
                            success = true,
                            failure = false,
                            data = it,
                            eventType = HomeState.EventType.NOW_PLAYING
                        )

                },{
                    state =
                        state.copy(
                            loading = false,
                            success = false,
                            failure = true,
                            message = it.localizedMessage,
                            eventType = HomeState.EventType.NOW_PLAYING
                        )
                })
        )
    }

    fun getUpcomingMovies() {
        Log.d(TAG, " >>> Trying to get upcoming movies")

        state = state.copy(loading = true, eventType = HomeState.EventType.UPCOMING_MOVIES)
        compositeDisposableNowPlaying.add(
            trendingMoviesRepoI.getUpcomingMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    state =
                        state.copy(
                            loading = false,
                            success = true,
                            failure = false,
                            data = it,
                            eventType = HomeState.EventType.UPCOMING_MOVIES
                        )

                },{
                    state =
                        state.copy(
                            loading = false,
                            success = false,
                            failure = true,
                            message = it.localizedMessage,
                            eventType = HomeState.EventType.UPCOMING_MOVIES
                        )
                })
        )
    }


    private fun publishState(state: HomeState) {
        stateObservable.postValue(state)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, " >>> Clearing compositeDisposable object")
        compositeDisposableNowPlaying.dispose()
        compositeDisposableUpcomingMovies.dispose()
    }

    companion object {
        const val TAG = "HomeViewModel"
    }

}