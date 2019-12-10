package com.dev.aman.movies_tmdb.ui.main.homeFragment

data class HomeState (
    var loading: Boolean = false,
    var success: Boolean = false,
    var failure: Boolean = false,
    var message: String? = null,
    var data: Any? = null,
    var eventType: EventType? = null
) {
    enum class EventType {
        TRENDING_MOVIE, TRENDING_TVSHOWS, NOW_PLAYING, UPCOMING_MOVIES
    }
}
