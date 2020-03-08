package com.dev.aman.movies_tmdb.data.repo.popular

import com.dev.aman.movies_tmdb.data.model.Common
import com.dev.aman.movies_tmdb.data.model.PopularPeople
import com.dev.aman.movies_tmdb.network.ApiInterface
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularRepo(private val populerApi: ApiInterface): PopularRepoI {

    override fun getPopularPeople(page: Int): Single<Common> {
        return Single.create<Common> { emitter ->
            populerApi.getPopularPeople(page).enqueue(object : Callback<Common>{

                override fun onResponse(call: Call<Common>, response: Response<Common>) {
                   response.body()?.let {
                       emitter.onSuccess(it)
                   }
                }

                override fun onFailure(call: Call<Common>, t: Throwable) {
                    emitter.onError(t)
                }
            })
        }
    }
}