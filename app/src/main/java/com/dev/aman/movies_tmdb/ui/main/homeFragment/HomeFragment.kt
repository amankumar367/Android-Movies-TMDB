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
import com.dev.aman.movies_tmdb.data.model.*
import com.dev.aman.movies_tmdb.data.repo.PopularRepo
import com.dev.aman.movies_tmdb.data.repo.TrendingMoviesRepo
import com.dev.aman.movies_tmdb.data.repo.TrendingTVShowsRepo
import com.dev.aman.movies_tmdb.extentions.invisible
import com.dev.aman.movies_tmdb.extentions.visible
import com.dev.aman.movies_tmdb.ui.adapter.*
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : DaggerFragment() {

    private lateinit var root : View
    private lateinit var homeViewModel: HomeViewModel
    private val trendingMoviesRepo = TrendingMoviesRepo()
    private val trendingTVShowsRepo = TrendingTVShowsRepo()
    private val popularRepo = PopularRepo()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)

        init()
        setObserver()
        loadData()

//        setScrollViewListner()
        setMoviesRecyclerView()

        return root
    }

    private fun init() {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.setRepository(trendingMoviesRepo, trendingTVShowsRepo, popularRepo)
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
            HomeState.EventType.NOW_PLAYING -> showLoading(root.pb_now_playing)
            HomeState.EventType.UPCOMING_MOVIES -> showLoading(root.pb_upcoming_movies)
            HomeState.EventType.POPULAR_PEOPLES -> showLoading(root.pb_popular_people)
        }
    }

    private fun showDataByEvent(state: HomeState) {
        when (state.eventType){
            HomeState.EventType.TRENDING_MOVIE -> {}
            HomeState.EventType.TRENDING_TVSHOWS -> setTVShowsRecyclerView(state.data as TrendingTVShows)
            HomeState.EventType.NOW_PLAYING -> setNowPlayingRecyclerView(state.data as NowPlaying)
            HomeState.EventType.UPCOMING_MOVIES -> setUpcomingMoviesRecyclerVuew(state.data as UpcomingMovies)
            HomeState.EventType.POPULAR_PEOPLES -> setPopularPeopleRecyclerView(state.data as PopularPeople)
        }
    }

    private fun showFailureByEvent(state: HomeState) {
        when (state.eventType) {
            HomeState.EventType.TRENDING_MOVIE -> {
                hideLoading(pb_trending_movies)
                Log.e(TAG, " >>> Error while fatching trending movies : ${state.message}")
            }
            HomeState.EventType.TRENDING_TVSHOWS -> {
                hideLoading(pb_trending_tvshows)
                Log.e(TAG, " >>> Error while fatching trending tv shows : ${state.message}")
            }
            HomeState.EventType.NOW_PLAYING -> {
                hideLoading(pb_now_playing)
                Log.e(TAG, " >>> Error while fatching now playing movies : ${state.message}")
            }
            HomeState.EventType.UPCOMING_MOVIES -> {
                hideLoading(pb_upcoming_movies)
                Log.e(TAG, " >>> Error while fatching upcoming movies : ${state.message}")
            }
            HomeState.EventType.POPULAR_PEOPLES -> {
                hideLoading(pb_popular_people)
                Log.e(TAG, " >>> Error while fatching popular peoples : ${state.message}")
            }
        }
    }

    private fun loadData() {
        homeViewModel.getTrendingMovies()
        homeViewModel.getTrendingTVShows()
        homeViewModel.getNowPlaying()
        homeViewModel.getUpcomingMovies()
        homeViewModel.getPopularPeoples()
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

    // TODO Setting up Adapter with livedata
    private fun setMoviesRecyclerView() {
        hideLoading(root.pb_trending_movies)
        root.rv_trending_movies.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)


        val adapter = TrendingMoviesAdapter()
        root.rv_trending_movies.adapter = adapter
        homeViewModel.moviesResult.observe(this, Observer {
            adapter.submitList(it)
        })

    }

    private fun setTVShowsRecyclerView(trendingTVShows: TrendingTVShows) {
        hideLoading(root.pb_trending_tvshows)
        root.rv_trending_tvShows.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        root.rv_trending_tvShows.adapter = trendingTVShows.results?.let { TrendingTVShowsAdapter(it) }
    }

    private fun setNowPlayingRecyclerView(nowPlaying: NowPlaying) {
        hideLoading(root.pb_now_playing)
        root.rv_now_playing.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        root.rv_now_playing.adapter = nowPlaying.results?.let { NowPlayingAdapter(it) }
    }

    private fun setUpcomingMoviesRecyclerVuew(upcomingMovies: UpcomingMovies) {
        hideLoading(root.pb_upcoming_movies)

        root.rv_upcoming_movies.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        root.rv_upcoming_movies.adapter = upcomingMovies.results?.let {  UpcomingMoviesAdapter(it) }
    }

    private fun setPopularPeopleRecyclerView(popularPeople: PopularPeople) {
        hideLoading(root.pb_popular_people)
        root.rv_popular_people.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        root.rv_popular_people.adapter = popularPeople.results?.let {  PopularPeopleAdapter(it) }

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