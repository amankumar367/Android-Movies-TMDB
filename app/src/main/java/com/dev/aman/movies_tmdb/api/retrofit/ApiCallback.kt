package com.dev.aman.movies_tmdb.api.retrofit

interface ApiCallback<T> {
    fun onSuccess(t: T)
    fun onFailure(message: String)
}