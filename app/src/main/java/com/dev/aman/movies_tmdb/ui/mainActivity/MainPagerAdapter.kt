package com.dev.aman.movies_tmdb.ui.mainActivity

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.dev.aman.movies_tmdb.ui.MainFragment
import com.dev.aman.movies_tmdb.ui.ProgressFragment
import com.dev.aman.movies_tmdb.ui.TrailerFragment

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> { MainFragment.mainFragmentInstance() }

            1 -> { TrailerFragment.trailerFragmentInstance() }

            2 -> { ProgressFragment.progressFragmentInstance() }

            else -> { MainFragment.mainFragmentInstance() }
        }
    }

    override fun getCount(): Int {
        return 3
    }
}
