package com.dev.aman.movies_tmdb.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.aman.movies_tmdb.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : DaggerFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, " >>> onViewCreated")

        setUpViewPagerAndTabLayout()

    }

    private fun setUpViewPagerAndTabLayout() {
        Log.d(TAG, " >>> Setting tabLayout to viewPager")
        vp_movies.adapter = MoviesViewPagerAdapter(activity!!.supportFragmentManager)
        vp_movies.offscreenPageLimit = 2
        tl_movies.setupWithViewPager(vp_movies)
    }

    companion object {
        private const val TAG = "MoviesFragment"
    }
}
