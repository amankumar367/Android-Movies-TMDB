package com.dev.aman.movies_tmdb

import android.os.Bundle
import android.util.Log
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, " >>> BaseActivity Created")

    }

    companion object {
        private var TAG = "BaseActivity"
    }
}