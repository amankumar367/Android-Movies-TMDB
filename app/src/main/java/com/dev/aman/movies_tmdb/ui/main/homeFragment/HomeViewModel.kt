package com.dev.aman.movies_tmdb.ui.main.homeFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.aman.movies_tmdb.api.data.TrendingMovies
import com.dev.aman.movies_tmdb.api.data.TrendingTVShows
import com.dev.aman.movies_tmdb.api.repo.TrendingMoviesRepoI
import com.dev.aman.movies_tmdb.api.repo.TrendingTVShowsRepoI
import com.dev.aman.movies_tmdb.api.retrofit.ApiCallback
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel: ViewModel() {

    var stateObservable: MutableLiveData<HomeState> = MutableLiveData()

    var compositeDisposable = CompositeDisposable()

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
        trendingMoviesRepoI.getTrendingMovies(object : ApiCallback<TrendingMovies>{
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
        trendingTVShowsRepoI.getTrendingTVShows(object : ApiCallback<TrendingTVShows>{
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


    private fun publishState(state: HomeState) {
        stateObservable.postValue(state)
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