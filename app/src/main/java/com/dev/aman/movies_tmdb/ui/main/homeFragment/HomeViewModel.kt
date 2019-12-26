package com.dev.aman.movies_tmdb.ui.main.homeFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dev.aman.movies_tmdb.data.model.TrendingMovies
import com.dev.aman.movies_tmdb.data.model.TrendingTVShows
import com.dev.aman.movies_tmdb.data.repo.PopularRepoI
import com.dev.aman.movies_tmdb.data.repo.TrendingMoviesRepoI
import com.dev.aman.movies_tmdb.data.repo.TrendingTVShowsRepoI
import com.dev.aman.movies_tmdb.network.ApiCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel: ViewModel() {

    lateinit var moviesResult: LiveData<PagedList<TrendingMovies.Result>>

    var stateObservable: MutableLiveData<HomeState> = MutableLiveData()

    private var compositeDisposable = CompositeDisposable()

    private lateinit var trendingMoviesRepoI: TrendingMoviesRepoI
    private lateinit var trendingTVShowsRepoI: TrendingTVShowsRepoI
    private lateinit var popularRepoI: PopularRepoI

    var state = HomeState()
        set(value) {
            field = value
            publishState(value)
        }

    fun setRepository(trendingMoviesRepoI: TrendingMoviesRepoI,
                      trendingTVShowsRepoI: TrendingTVShowsRepoI,
                      popularRepoI: PopularRepoI) {
        Log.d(TAG, " >>> Setting up Repository")

        this.trendingMoviesRepoI = trendingMoviesRepoI
        this.trendingTVShowsRepoI = trendingTVShowsRepoI
        this.popularRepoI = popularRepoI
    }

    fun getTrendingMovies() {
        Log.d(TAG, " >>> Trying to get trending movies")

        moviesResult = trendingMoviesRepoI.getTrendingMovies()

        state = state.copy(
            loading = false,
            success = true,
            failure = false,
            data = moviesResult.value,
            eventType = HomeState.EventType.TRENDING_MOVIE
        )
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
        compositeDisposable.add(
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
        compositeDisposable.add(
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

    fun getPopularPeoples() {
        Log.d(TAG, " >>> Trying to get popular peoples")

        state = state.copy(loading = true, eventType = HomeState.EventType.POPULAR_PEOPLES)
        compositeDisposable.add(
            popularRepoI.getPopularPeople()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    state =
                        state.copy(
                            loading = false,
                            success = true,
                            failure = false,
                            data = it,
                            eventType = HomeState.EventType.POPULAR_PEOPLES
                        )
                    }, {
                        state =
                            state.copy(
                                loading = false,
                                success = false,
                                failure = true,
                                message = it.localizedMessage,
                                eventType = HomeState.EventType.POPULAR_PEOPLES
                            )
                    }
                )
        )
    }


    private fun publishState(state: HomeState) {
        Log.d(TAG," >>> Publish State : $state")
        stateObservable.value = state
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