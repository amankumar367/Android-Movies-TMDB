package com.dev.aman.movies_tmdb.data.repo.movies

import com.dev.aman.movies_tmdb.data.model.Common
import com.dev.aman.movies_tmdb.network.ApiInterface
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepo(private val moviesApi: ApiInterface) : MoviesRepoI {

    override fun getTrendingMovies(page: Int): Single<Common> {
        return Single.create<Common> { emitter ->
            moviesApi.getTrendingWeekMovies(page).enqueue(object : Callback<Common> {
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

    override fun getNowPlaying(page: Int): Single<Common> {
        return Single.create<Common> { emitter ->
            moviesApi.getNowPlaying(page).enqueue(object : Callback<Common>{

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

    override fun getUpcomingMovies(page: Int): Single<Common> {
        return Single.create<Common> { emitter ->
            moviesApi.getUpcomingMovies(page).enqueue(object : Callback<Common>{

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