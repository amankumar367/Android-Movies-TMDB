package com.dev.aman.movies_tmdb


import android.util.Log
import com.dev.aman.movies_tmdb.di.AppComponent
import com.dev.aman.movies_tmdb.di.DaggerAppComponent
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {


    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {

        Log.d(TAG, " >>> BaseApplication Created")

        if (BuildConfig.DEBUG) {
            Log.d(TAG, " >>> Initializing Stetho")
            Stetho.initializeWithDefaults(this)
        }

        appComponent = DaggerAppComponent.builder().application(this).build()

        return appComponent
    }

    companion object {
        private val TAG = BaseApplication::class.java.simpleName

        private var appComponent: AppComponent? = null

        fun getAppComponent(): AppComponent? {
            return appComponent
        }
    }
}