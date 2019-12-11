package com.dev.aman.movies_tmdb.data.repo

import com.dev.aman.movies_tmdb.BaseApplication
import com.dev.aman.movies_tmdb.data.model.PopularPeople
import com.dev.aman.movies_tmdb.network.ApiInterface
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class PopularRepo: PopularRepoI {

    @Inject
    lateinit var retrofit: Retrofit

    private var populerApi: ApiInterface? = null

    init {
        BaseApplication.getAppComponent()!!.inject(this)
        populerApi = retrofit.create(ApiInterface::class.java)
    }

    override fun getPopularPeople(): Single<PopularPeople> {

        return Single.create<PopularPeople> { emitter ->
            populerApi!!.getPopularPeople().enqueue(object : Callback<PopularPeople>{

                override fun onResponse(call: Call<PopularPeople>, response: Response<PopularPeople>) {
                   response.body()?.let {
                       emitter.onSuccess(it)
                   }
                }

                override fun onFailure(call: Call<PopularPeople>, t: Throwable) {
                    emitter.onError(t)
                }
            })
        }
    }
}