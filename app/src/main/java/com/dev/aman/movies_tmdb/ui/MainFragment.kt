package com.dev.aman.movies_tmdb.ui

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.dev.aman.movies_tmdb.R
import com.dev.aman.movies_tmdb.api.data.TrendingMovies
import com.dev.aman.movies_tmdb.api.repo.TrendingMoviesRepo
import com.dev.aman.movies_tmdb.api.retrofit.ApiCallback
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.view.*


class MainFragment : DaggerFragment() {

    private lateinit var root : View
    private val trendingMoviesRepoI = TrendingMoviesRepo()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_main, container, false)

        setScrollViewListner()
        getTrendingMoviesList()

        return root
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun setScrollViewListner() {
        root.fragment_main_scrollview.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if(scrollY - oldScrollY > 0){
                Log.d(TAG, "Scroll DOWN")
                (activity as AppCompatActivity).supportActionBar!!.hide()
            }else {
                Log.d(TAG, "Scroll UP")
                (activity as AppCompatActivity).supportActionBar!!.show()
            }
        }
    }

    private fun getTrendingMoviesList() {
        trendingMoviesRepoI.getTrendingMovies(object : ApiCallback<TrendingMovies>{
            override fun onSuccess(t: TrendingMovies) {
                Log.d(TAG, "Success Response : $t")
            }

            override fun onFailure(message: String) {
                Log.d(TAG, "Failure Response : $message")
            }
        })
    }

    companion object{
        private var TAG = MainFragment::class.java.simpleName

        fun mainFragmentInstance() : MainFragment {
            return MainFragment()
        }
    }
}