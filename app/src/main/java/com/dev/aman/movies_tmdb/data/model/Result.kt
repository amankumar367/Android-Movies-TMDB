package com.dev.aman.movies_tmdb.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(
    @SerializedName("adult")
    var adult: Boolean = false,

    @SerializedName("backdrop_path")
    var backdropPath: String?,

    @SerializedName("first_air_date")
    var first_air_date: String?,

    @SerializedName("genre_ids")
    var genreIds: List<Int> = listOf(),

    @SerializedName("id")
    var id: Long?,

    @SerializedName("known_for_department")
    var known_for_department: String?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("original_language")
    var originalLanguage: String?,

    @SerializedName("original_title")
    var originalTitle: String?,

    @SerializedName("overview")
    var overview: String?,

    @SerializedName("popularity")
    var popularity: Double?,

    @SerializedName("poster_path")
    var posterPath: String?,

    @SerializedName("profile_path")
    var profilePath: String?,

    @SerializedName("release_date")
    var releaseDate: String?,

    @SerializedName("title")
    var title: String?,

    @SerializedName("video")
    var video: Boolean = false,

    @SerializedName("vote_average")
    var voteAverage: Double?,

    @SerializedName("vote_count")
    var voteCount: Int?
) : Parcelable