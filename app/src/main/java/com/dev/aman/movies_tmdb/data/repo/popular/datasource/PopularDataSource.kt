package com.dev.aman.movies_tmdb.data.repo.popular.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.dev.aman.movies_tmdb.data.model.Result
import com.dev.aman.movies_tmdb.data.repo.popular.PopularRepoI
import com.dev.aman.movies_tmdb.network.RequestType
import com.dev.aman.movies_tmdb.ui.main.homeFragment.HomeState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PopularDataSource(
    private val popularRepoI: PopularRepoI,
    private val compositeDisposable: CompositeDisposable,
    private val requestType: RequestType
) : PageKeyedDataSource<Int, Result>() {

    private var lastRequestedPage = 1
    val stateObservable: MutableLiveData<HomeState> = MutableLiveData()

    var state = HomeState()
        set(value) {
            field = value
            publishState(value)
        }

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {
        Log.d(TAG, " >>> Received call to loadInitial for popular people -> Request Type: $requestType")
        state = state.copy(initialLoading = true, success = false, failure = false, message = null, eventType = requestType)
        compositeDisposable.add(
            popularRepoI.getPopularPeople(lastRequestedPage++)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    state = state.copy(initialLoading = false, success = true, eventType = requestType)
                    callback.onResult(it.results, null, 2)
                }, {
                    retry = { loadInitial(params, callback) }
                    state = state.copy(initialLoading = false, failure = true, message = it.localizedMessage, eventType = requestType)
                })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        Log.d(TAG, " >>> Received call to loadAfter for popular people")
        state = state.copy(afterLoading = true, success = false, failure = false, message = null, eventType = requestType)
        compositeDisposable.add(
            popularRepoI.getPopularPeople(lastRequestedPage++)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    state = state.copy(afterLoading = false, success = true, eventType = requestType)
                    if (it.totalPages >= params.key) {
                        callback.onResult(it.results, params.key + 1)
                    }
                }, {
                    state = state.copy(afterLoading = false, failure = true, message = it.localizedMessage, eventType = requestType)
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {

    }

    fun retry() {
        Log.d(TAG, " >>> Received call to retry popular people request")
        val prevRetry = retry
        retry = null
        lastRequestedPage = 1
        prevRetry?.invoke()
    }

    private fun publishState(state: HomeState) {
        Log.d(TAG, " >>> Publish State : $state -> Request Type: $requestType")
        stateObservable.postValue(state)
    }

    companion object {
        const val TAG = "PopularDataSource"
    }

}