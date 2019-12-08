package com.dev.aman.movies_tmdb.data.repo

import com.dev.aman.movies_tmdb.BaseApplication
import com.dev.aman.movies_tmdb.data.model.TrendingTVShows
import com.dev.aman.movies_tmdb.network.ApiCallback
import com.dev.aman.movies_tmdb.network.ApiInterface
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

class TrendingTVShowsRepo : TrendingTVShowsRepoI {

    @Inject
    lateinit var retrofit : Retrofit

    private var trendingTVShowsApi: ApiInterface? = null

    init {
        BaseApplication.getAppComponent()!!.inject(this)
        trendingTVShowsApi = retrofit.create(ApiInterface::class.java)
    }

    override fun getTrendingTVShows(apiCallback: ApiCallback<TrendingTVShows>) {
        trendingTVShowsApi!!.getTrendingWeekTVShows()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<TrendingTVShows> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: TrendingTVShows) {
                    apiCallback.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    apiCallback.onFailure(e.localizedMessage!!)
                }
            })

    }
}