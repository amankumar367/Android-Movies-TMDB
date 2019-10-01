package com.dev.aman.movies_tmdb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "BaseFragment Created")

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        private var TAG = BaseFragment::class.java.simpleName
    }
}