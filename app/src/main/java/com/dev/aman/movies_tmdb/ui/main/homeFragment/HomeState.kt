package com.dev.aman.movies_tmdb.ui.main.homeFragment

import com.dev.aman.movies_tmdb.network.RequestType

data class HomeState (
    var initialLoading: Boolean = false,
    var afterLoading: Boolean = false,
    var success: Boolean = false,
    var failure: Boolean = false,
    var message: String? = null,
    var eventType: RequestType? = null
)
