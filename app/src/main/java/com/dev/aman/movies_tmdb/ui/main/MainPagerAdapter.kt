package com.dev.aman.movies_tmdb.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dev.aman.movies_tmdb.ui.main.homeFragment.HomeFragment
import com.dev.aman.movies_tmdb.ui.main.progressFragment.ProgressFragment
import com.dev.aman.movies_tmdb.ui.main.trailerFragment.TrailerFragment

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


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
}
