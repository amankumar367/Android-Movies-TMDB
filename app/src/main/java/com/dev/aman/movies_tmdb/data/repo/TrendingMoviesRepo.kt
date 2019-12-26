package com.dev.aman.movies_tmdb.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.dev.aman.movies_tmdb.BaseApplication
import com.dev.aman.movies_tmdb.data.model.NowPlaying
import com.dev.aman.movies_tmdb.data.model.TrendingMovies
import com.dev.aman.movies_tmdb.data.model.UpcomingMovies
import com.dev.aman.movies_tmdb.network.ApiInterface
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.Executors
import javax.inject.Inject

class TrendingMoviesRepo : TrendingMoviesRepoI {

    @Inject
    lateinit var retrofit : Retrofit

    private var moviesApi: ApiInterface? = null

    init {
        BaseApplication.getAppComponent()!!.inject(this)
        moviesApi = retrofit.create(ApiInterface::class.java)
    }

    override fun getTrendingMovies(): LiveData<PagedList<TrendingMovies.Result>> {
        val sourceFactory = DataSourceFactory(moviesApi!!)

        val livePageList =  sourceFactory.toLiveData(
            // we use Config Kotlin ext. function here, could also use PagedList.Config.Builder
            config = Config(
                pageSize = 10,
                enablePlaceholders = true,
                initialLoadSizeHint = 10),
        // provide custom executor for network requests, otherwise it will default to
        // Arch Components' IO pool which is also used for disk access
        fetchExecutor = Executors.newFixedThreadPool(5))

        return livePageList

//        moviesApi!!.getTrendingWeekMovies()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : Observer<TrendingMovies> {
//                override fun onComplete() {
//                }
//
//                override fun onSubscribe(d: Disposable) {
//                }
//
//                override fun onNext(t: TrendingMovies) {
//                    apiCallback.onSuccess(t)
//                }
//
//                override fun onError(e: Throwabl`e) {
//                    apiCallback.onFailure(e.localizedMessage!!)
//                }
//            })
    }

    override fun getNowPlaying(): Single<NowPlaying> {
        return Single.create<NowPlaying> { emitter ->
            moviesApi!!.getNowPlaying().enqueue(object : Callback<NowPlaying>{

                override fun onResponse(call: Call<NowPlaying>, response: Response<NowPlaying>) {
                    response.body()?.let {
                        emitter.onSuccess(it)
                    }
                }

                override fun onFailure(call: Call<NowPlaying>, t: Throwable) {
                    emitter.onError(t)
                }
            })
        }

    }

    override fun getUpcomingMovies(): Single<UpcomingMovies> {
        return Single.create<UpcomingMovies> { emitter ->
            moviesApi!!.getUpcomingMovies().enqueue(object : Callback<UpcomingMovies>{

                override fun onResponse(call: Call<UpcomingMovies>, response: Response<UpcomingMovies>) {
                    response.body()?.let {
                        emitter.onSuccess(it)
                    }
                }

                override fun onFailure(call: Call<UpcomingMovies>, t: Throwable) {
                    emitter.onError(t)
                }
            })
        }
    }
}