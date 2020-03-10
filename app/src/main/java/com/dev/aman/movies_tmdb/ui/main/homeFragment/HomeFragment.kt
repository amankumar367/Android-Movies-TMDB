package com.dev.aman.movies_tmdb.ui.main.homeFragment

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.aman.movies_tmdb.R
import com.dev.aman.movies_tmdb.data.repo.movies.MoviesRepoI
import com.dev.aman.movies_tmdb.data.repo.popular.PopularRepoI
import com.dev.aman.movies_tmdb.data.repo.tvshows.TVShowsRepoI
import com.dev.aman.movies_tmdb.databinding.FragmentHomeBinding
import com.dev.aman.movies_tmdb.extentions.createFactory
import com.dev.aman.movies_tmdb.extentions.gone
import com.dev.aman.movies_tmdb.extentions.invisible
import com.dev.aman.movies_tmdb.extentions.visible
import com.dev.aman.movies_tmdb.network.RequestType
import com.dev.aman.movies_tmdb.ui.adapter.*
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import javax.inject.Inject


class HomeFragment : DaggerFragment() {

    private lateinit var root : View
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var databinding: FragmentHomeBinding

    private lateinit var trendingMoviesAdapter: CommonAdapter
    private lateinit var trendingTVShowsAdapter: CommonAdapter
    private lateinit var nowPlayingAdapter: CommonAdapter
    private lateinit var upcomingMoviesAdapter: CommonAdapter
    private lateinit var popularPeopleAdapter: CommonAdapter
    private var topPickAdapter = CommonAdapter(RequestType.TOP_PICKS)

    private var isNextLoaderShowing: Boolean = false

    @Inject
    lateinit var moviesRepo: MoviesRepoI

    @Inject
    lateinit var tvShowsRepo: TVShowsRepoI

    @Inject
    lateinit var popularRepo: PopularRepoI

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        init()
        loadData()
        setStateObserver()
        setPagingObserver()
        setAllRecyclerView()

//        setScrollViewListner()

        return root
    }

    private fun init() {
        root = databinding.root
        val factory
                = HomeViewModel(moviesRepo, tvShowsRepo, popularRepo).createFactory()
        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
    }

    private fun loadData() {
        homeViewModel.getTrendingTVShows()
        homeViewModel.getNowPlaying()
        homeViewModel.getUpcomingMovies()
        homeViewModel.getPopularPeoples()
        homeViewModel.getTrendingMovies()
    }

    private fun setStateObserver() {
        homeViewModel.trendingMoviesStateObservable.observe(this, Observer {
            updateView(it)
        })
        homeViewModel.trendingTVShowsStateObservable.observe(this, Observer {
            updateView(it)
        })
        homeViewModel.nowPlayingStateObservable.observe(this, Observer {
            updateView(it)
        })
        homeViewModel.upcomingMoviesStateObservable.observe(this, Observer {
            updateView(it)
        })
        homeViewModel.popularPeopleStateObservable.observe(this, Observer {
            updateView(it)
        })
    }

    private fun setPagingObserver() {
        homeViewModel.trendingMoviesPageList.observe(this, Observer {
            trendingMoviesAdapter.submitList(it)
        })
        homeViewModel.trendingTVShowsPageList.observe(this, Observer {
            trendingTVShowsAdapter.submitList(it)
        })
        homeViewModel.nowPlayingPageList.observe(this, Observer {
            nowPlayingAdapter.submitList(it)
        })
        homeViewModel.upcomingMoviesPageList.observe(this, Observer {
            upcomingMoviesAdapter.submitList(it)
        })
        homeViewModel.popularPeoplePageList.observe(this, Observer {
            popularPeopleAdapter.submitList(it)
        })
    }

    private fun setAllRecyclerView() {
        setMoviesRecyclerView()
        setTVShowsRecyclerView()
        setNowPlayingRecyclerView()
        setUpcomingMoviesRecyclerVuew()
        setPopularPeopleRecyclerView()
    }

    private fun updateView(state: HomeState?) {
        Log.d(TAG, " >>> Updating view for ${state?.eventType} with current state : $state")
        when {
            state!!.initialLoading -> showInitialLoading(state)
            state.afterLoading -> showAfterLoading(state)
            state.success -> setData(state)
            state.failure -> showFailureByEvent(state)
        }
    }

    private fun showInitialLoading(state: HomeState) {
        when (state.eventType) {
            RequestType.TRENDING_MOVIE -> {
                show(root.pb_trending_movies)
                hide(root.trending_movies_retry)
            }
            RequestType.TRENDING_TVSHOWS -> {
                show(root.pb_trending_tvshows)
                hide(root.trending_tvShows_retry)
            }
            RequestType.NOW_PLAYING -> {
                show(root.pb_now_playing)
                hide(root.now_playing_retry)
            }
            RequestType.UPCOMING_MOVIES -> {
                show(root.pb_upcoming_movies)
                hide(root.upcoming_movies_retry)
            }
            RequestType.POPULAR_PEOPLES -> {
                show(root.pb_popular_people)
                hide(root.popular_people_retry)
            }
            RequestType.TOP_PICKS -> {
                show(root.pb_top_picks)
                hide(root.top_picks_retry)
            }
        }
    }

    private fun showAfterLoading(state: HomeState) {
        isNextLoaderShowing = true
        when (state.eventType) {
            RequestType.TRENDING_MOVIE -> show(root.pb_next_trending_movies)
            RequestType.TRENDING_TVSHOWS -> show(root.pb_next_trending_tvShows)
            RequestType.NOW_PLAYING -> show(root.pb_next_now_playing)
            RequestType.UPCOMING_MOVIES -> show(root.pb_next_upcoming_movies)
            RequestType.POPULAR_PEOPLES -> show(root.pb_next_popular_people)
            RequestType.TOP_PICKS -> show(root.pb_next_top_picks)
        }
    }

    private fun setData(state: HomeState) {
        when (state.eventType) {
            RequestType.TRENDING_MOVIE -> {
                hide(root.pb_trending_movies)
                gone(root.pb_next_trending_movies)
            }
            RequestType.TRENDING_TVSHOWS -> {
                hide(root.pb_trending_tvshows)
                gone(root.pb_next_trending_tvShows)
            }
            RequestType.NOW_PLAYING -> {
                hide(root.pb_now_playing)
                gone(root.pb_next_now_playing)
            }
            RequestType.UPCOMING_MOVIES -> {
                hide(root.pb_upcoming_movies)
                gone(root.pb_next_upcoming_movies)
            }
            RequestType.POPULAR_PEOPLES -> {
                hide(root.pb_popular_people)
                gone(root.pb_next_popular_people)
            }
            RequestType.TOP_PICKS -> {}
        }
    }

    private fun showFailureByEvent(state: HomeState) {
        when (state.eventType) {
            RequestType.TRENDING_MOVIE -> {
                Log.e(TAG, " >>> Error while fetching trending movies : ${state.message}")
                if (isNextLoaderShowing) {
                    gone(root.pb_next_trending_movies)
                    isNextLoaderShowing = false
                } else {
                    hide(root.pb_trending_movies)
                    show(root.trending_movies_retry)
                }
            }
            RequestType.TRENDING_TVSHOWS -> {
                Log.e(TAG, " >>> Error while fetching trending tv shows : ${state.message}")
                if (isNextLoaderShowing) {
                    gone(root.pb_next_trending_tvShows)
                    isNextLoaderShowing = false
                } else {
                    hide(pb_trending_tvshows)
                    show(root.trending_tvShows_retry)
                }
            }
            RequestType.NOW_PLAYING -> {
                Log.e(TAG, " >>> Error while fetching now playing movies : ${state.message}")
                if (isNextLoaderShowing) {
                    gone(root.pb_next_now_playing)
                    isNextLoaderShowing = false
                } else {
                    hide(pb_now_playing)
                    show(root.now_playing_retry)
                }
            }
            RequestType.UPCOMING_MOVIES -> {
                Log.e(TAG, " >>> Error while fetching upcoming movies : ${state.message}")
                if (isNextLoaderShowing) {
                    gone(root.pb_next_upcoming_movies)
                    isNextLoaderShowing = false
                } else {
                    hide(pb_upcoming_movies)
                    show(root.upcoming_movies_retry)
                }
            }
            RequestType.POPULAR_PEOPLES -> {
                Log.e(TAG, " >>> Error while fetching popular peoples : ${state.message}")
                if (isNextLoaderShowing) {
                    gone(root.pb_next_popular_people)
                    isNextLoaderShowing = false
                } else {
                    hide(pb_popular_people)
                    show(root.popular_people_retry)
                }
            }
            RequestType.TOP_PICKS -> {}
        }
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

    private fun setMoviesRecyclerView() {
        root.rv_trending_movies.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        trendingMoviesAdapter = CommonAdapter(RequestType.TRENDING_MOVIE)
        root.rv_trending_movies.adapter = trendingMoviesAdapter
    }

    private fun setTVShowsRecyclerView() {
        root.rv_trending_tvShows.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        trendingTVShowsAdapter = CommonAdapter(RequestType.TRENDING_TVSHOWS)
        root.rv_trending_tvShows.adapter = trendingTVShowsAdapter
    }

    private fun setNowPlayingRecyclerView() {
        root.rv_now_playing.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        nowPlayingAdapter = CommonAdapter(RequestType.NOW_PLAYING)
        root.rv_now_playing.adapter = nowPlayingAdapter
    }

    private fun setUpcomingMoviesRecyclerVuew() {
        root.rv_upcoming_movies.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        upcomingMoviesAdapter = CommonAdapter(RequestType.UPCOMING_MOVIES)
        root.rv_upcoming_movies.adapter = upcomingMoviesAdapter
    }

    private fun setPopularPeopleRecyclerView() {
        root.rv_popular_people.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        popularPeopleAdapter = CommonAdapter(RequestType.POPULAR_PEOPLES)
        root.rv_popular_people.adapter = popularPeopleAdapter
    }

    private fun show(view: View) {
        view.visible()
    }

    private fun hide(view: View) {
        view.invisible()
    }

    private fun gone(view: View) {
        view.gone()
    }

    companion object{
        private var TAG = "HomeFragment"

        fun homeFragmentInstance() : HomeFragment {
            return HomeFragment()
        }
    }
}