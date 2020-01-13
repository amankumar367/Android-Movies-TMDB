package com.dev.aman.movies_tmdb.network

import com.dev.aman.movies_tmdb.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


class NetworkInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest= chain.request()

        val url = originalRequest.url().newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }
}