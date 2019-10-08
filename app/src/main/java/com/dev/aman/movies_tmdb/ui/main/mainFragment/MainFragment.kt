package com.dev.aman.movies_tmdb.ui.main.mainFragment

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.aman.movies_tmdb.R
import com.dev.aman.movies_tmdb.api.data.TrendingMovies
import com.dev.aman.movies_tmdb.api.data.TrendingTVShows
import com.dev.aman.movies_tmdb.api.repo.TrendingMoviesRepo
import com.dev.aman.movies_tmdb.api.repo.TrendingTVShowsRepo
import com.dev.aman.movies_tmdb.api.retrofit.ApiCallback
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.view.*


class MainFragment : DaggerFragment() {

    private lateinit var root : View
    private val trendingMoviesRepoI = TrendingMoviesRepo()
    private val trendingTVShowsRepoI = TrendingTVShowsRepo()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_main, container, false)

        setScrollViewListner()
        getTrendingMoviesList()
        getTrendingTVShowsList()

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
        root.pb_trending_movies.visibility = View.VISIBLE
        trendingMoviesRepoI.getTrendingMovies(object : ApiCallback<TrendingMovies>{
            override fun onSuccess(t: TrendingMovies) {
                Log.d(TAG, "Trendnig Movies Success Response : $t")
                setMoviesRecyclerView(t)
            }

            override fun onFailure(message: String) {
                Log.d(TAG, "Trendnig Movies Failure Response : $message")
            }
        })
    }

    private fun getTrendingTVShowsList() {
        root.pb_trending_tvshows.visibility = View.VISIBLE
        trendingTVShowsRepoI.getTrendingTVShows(object :ApiCallback<TrendingTVShows>{
            override fun onSuccess(t: TrendingTVShows) {
                Log.d(TAG, "Trendnig TVShows Success Response : $t")
                setTVShowsRecyclerView(t)
            }

            override fun onFailure(message: String) {
                Log.d(TAG, "Trendnig TVShows Failure Response : $message")
            }
        })

    }

    private fun setMoviesRecyclerView(trendingMovies: TrendingMovies) {
        root.pb_trending_movies.visibility = View.INVISIBLE
        root.rv_trending_movies.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        root.rv_trending_movies.adapter = trendingMovies.results?.let { TrendingMoviesAdapter(it) }
    }

    private fun setTVShowsRecyclerView(trendingTVShows: TrendingTVShows) {
        root.pb_trending_tvshows.visibility = View.INVISIBLE
        root.rv_trending_tvShows.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        root.rv_trending_tvShows.adapter = trendingTVShows.results?.let { TrendingTVShowsAdapter(it) }
    }

    companion object{
        private var TAG = MainFragment::class.java.simpleName

        fun mainFragmentInstance() : MainFragment {
            return MainFragment()
        }
    }
}