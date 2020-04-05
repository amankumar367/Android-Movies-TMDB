package com.dev.aman.movies_tmdb.ui.movies

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dev.aman.movies_tmdb.ui.main.homeFragment.HomeFragment
import com.dev.aman.movies_tmdb.ui.main.progressFragment.ProgressFragment
import com.dev.aman.movies_tmdb.ui.main.trailerFragment.TrailerFragment

class MoviesViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> { HomeFragment.homeFragmentInstance() }

            1 -> { TrailerFragment.trailerFragmentInstance() }

            2 -> { ProgressFragment.progressFragmentInstance() }

            else -> { HomeFragment.homeFragmentInstance() }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Now Playing"
            1 -> "Upcoming"
            2 -> "Trending"
            else -> ""
        }
    }
}
