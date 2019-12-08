package com.dev.aman.movies_tmdb.ui.main.homeFragment

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.aman.movies_tmdb.R
import com.dev.aman.movies_tmdb.data.model.TrendingMovies
import com.dev.aman.movies_tmdb.data.model.TrendingTVShows
import com.dev.aman.movies_tmdb.data.repo.TrendingMoviesRepo
import com.dev.aman.movies_tmdb.data.repo.TrendingTVShowsRepo
import com.dev.aman.movies_tmdb.extentions.invisible
import com.dev.aman.movies_tmdb.extentions.visible
import com.dev.aman.movies_tmdb.ui.adapter.TrendingMoviesAdapter
import com.dev.aman.movies_tmdb.ui.adapter.TrendingTVShowsAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : DaggerFragment() {

    private lateinit var root : View
    private lateinit var homeViewModel: HomeViewModel
    private val trendingMoviesRepo = TrendingMoviesRepo()
    private val trendingTVShowsRepo = TrendingTVShowsRepo()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)

        init()
        setObserver()
        loadData()

        setScrollViewListner()

        return root
    }

    private fun init() {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.setRepository(trendingMoviesRepo, trendingTVShowsRepo)
    }

    private fun setObserver() {
        homeViewModel.stateObservable.observe(this, Observer {
            updateView(it)
        })
    }

    private fun updateView(state: HomeState?) {
        when {
            state!!.loading -> showLoadingByEvent(state)
            state.success -> showDataByEvent(state)
            state.failure -> showFailureByEvent(state)
        }
    }

    private fun showLoadingByEvent(state: HomeState) {
        when (state.eventType) {
            HomeState.EventType.TRENDING_MOVIE -> showLoading(root.pb_trending_movies)
            HomeState.EventType.TRENDING_TVSHOWS -> showLoading(root.pb_trending_tvshows)
        }
    }

    private fun showDataByEvent(state: HomeState) {
        when (state.eventType){
            HomeState.EventType.TRENDING_MOVIE -> setMoviesRecyclerView(state.data as TrendingMovies)
            HomeState.EventType.TRENDING_TVSHOWS -> setTVShowsRecyclerView(state.data as TrendingTVShows)
        }
    }

    private fun showFailureByEvent(state: HomeState) {
        when (state.eventType) {
            HomeState.EventType.TRENDING_MOVIE -> {}
            HomeState.EventType.TRENDING_TVSHOWS -> {}
        }
    }

    private fun loadData() {
        homeViewModel.getTrendingMovies()
        homeViewModel.getTrendingTVShows()
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

    private fun setMoviesRecyclerView(trendingMovies: TrendingMovies) {
        hideLoading(root.pb_trending_movies)
        root.rv_trending_movies.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        root.rv_trending_movies.adapter = trendingMovies.results?.let { TrendingMoviesAdapter(it) }
    }

    private fun setTVShowsRecyclerView(trendingTVShows: TrendingTVShows) {
        hideLoading(root.pb_trending_tvshows)
        root.rv_trending_tvShows.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        root.rv_trending_tvShows.adapter = trendingTVShows.results?.let { TrendingTVShowsAdapter(it) }
    }

    private fun showLoading(view: View) {
        view.visible()
    }

    private fun hideLoading(view: View) {
        view.invisible()
    }

    companion object{
        private var TAG = "HomeFragment"

        fun homeFragmentInstance() : HomeFragment {
            return HomeFragment()
        }
    }
}