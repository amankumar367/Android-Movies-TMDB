package com.dev.aman.movies_tmdb.data.repo

import android.util.Log
import androidx.paging.ItemKeyedDataSource
import com.dev.aman.movies_tmdb.data.model.TrendingMovies
import com.dev.aman.movies_tmdb.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemKeyedMoviesDataSource(
    private val movieApi: ApiInterface)
    : ItemKeyedDataSource<String, TrendingMovies.Result>() {

    private var lastRequestedPage = 1

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<TrendingMovies.Result>
    ) {
        movieApi.getTrendingWeekMovies(lastRequestedPage++).enqueue(object: Callback<TrendingMovies> {
            override fun onResponse(
                call: Call<TrendingMovies>,
                response: Response<TrendingMovies>
            ) {
                Log.d(TAG, "Hitting URL : ${call.request().url()}")
                response.body()?.let {
                    callback.onResult(it.results!!)
                }
            }

            override fun onFailure(call: Call<TrendingMovies>, t: Throwable) {
                Log.d(TAG, "Hitting URL : ${call.request().url()}")
                Log.d(TAG, "Something went wrong ${t.localizedMessage}")

                --lastRequestedPage
                loadInitial(params, callback)
            }
        })

    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<TrendingMovies.Result>
    ) {
        movieApi.getTrendingWeekMovies(lastRequestedPage++).enqueue(object: Callback<TrendingMovies> {
            override fun onResponse(
                call: Call<TrendingMovies>,
                response: Response<TrendingMovies>
            ) {
                Log.d(TAG, "Hitting URL : ${call.request().url()}")
                response.body()?.let {
                    callback.onResult(it.results!!)
                }
            }

            override fun onFailure(call: Call<TrendingMovies>, t: Throwable) {
                Log.d(TAG, "Hitting URL : ${call.request().url()}")
                Log.d(TAG, "Something went wrong ${t.localizedMessage}")

                --lastRequestedPage
                loadAfter(params, callback)
            }
        })

    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<TrendingMovies.Result>
    ) {

    }

    override fun getKey(item: TrendingMovies.Result): String = item.id!!.toString()


    companion object {
        private const val TAG = "ItemMoviesDataSource"
    }
}