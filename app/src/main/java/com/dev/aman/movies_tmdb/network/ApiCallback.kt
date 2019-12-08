package com.dev.aman.movies_tmdb.network

interface ApiCallback<T> {
    fun onSuccess(t: T)
    fun onFailure(message: String)
}