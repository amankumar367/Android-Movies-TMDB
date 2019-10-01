package com.dev.aman.movies_tmdb


import android.util.Log
import com.dev.aman.movies_tmdb.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {

        Log.d(TAG, "BaseApplication Created")

        return DaggerAppComponent.builder().application(this).build()
    }

    companion object {
        private val TAG = BaseApplication::class.java.simpleName
    }
}