package com.dev.aman.movies_tmdb.ui.main.trailerFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.aman.movies_tmdb.R
import dagger.android.support.DaggerFragment

class TrailerFragment : DaggerFragment() {

    private lateinit var root : View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_trailers, container, false)

        return root
    }

    companion object{
        fun trailerFragmentInstance() : TrailerFragment {
            return TrailerFragment()
        }
    }
}