package com.dev.aman.movies_tmdb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dev.aman.movies_tmdb.R

class TrailerFragment : Fragment() {

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
        fun trailerFragmentInstance() : TrailerFragment{
            return TrailerFragment()
        }
    }
}