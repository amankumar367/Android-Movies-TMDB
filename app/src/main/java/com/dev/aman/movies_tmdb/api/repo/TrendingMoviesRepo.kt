package com.dev.aman.movies_tmdb.api.repo

import com.dev.aman.movies_tmdb.BaseApplication
import com.dev.aman.movies_tmdb.api.data.TrendingMovies
import com.dev.aman.movies_tmdb.api.retrofit.ApiCallback
import com.dev.aman.movies_tmdb.api.retrofit.ApiInterface
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

class TrendingMoviesRepo : TrendingMoviesRepoI {

    @Inject
    lateinit var retrofit : Retrofit

    private var trendingMoviesApi: ApiInterface? = null

    init {
        BaseApplication.getAppComponent()!!.inject(this)
        trendingMoviesApi = retrofit.create(ApiInterface::class.java)
    }

    override fun getTrendingMovies(apiCallback: ApiCallback<TrendingMovies>) {
        trendingMoviesApi!!.getTrendingWeekMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<TrendingMovies> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: TrendingMovies) {
                    apiCallback.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    apiCallback.onFailure(e.localizedMessage!!)
                }
            })
    }
}