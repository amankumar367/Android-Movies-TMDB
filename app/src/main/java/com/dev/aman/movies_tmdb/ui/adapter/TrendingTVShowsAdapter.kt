package com.dev.aman.movies_tmdb.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.aman.movies_tmdb.R
import com.dev.aman.movies_tmdb.data.model.TrendingTVShows
import com.dev.aman.movies_tmdb.utils.ApiConstants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_single_grid.view.*

class TrendingTVShowsAdapter(var results: List<TrendingTVShows.Result?>)
    : RecyclerView.Adapter<TrendingTVShowsAdapter.TrendingTVShowsVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingTVShowsVH {
        return TrendingTVShowsVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_single_grid, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: TrendingTVShowsVH, position: Int) {
        holder.bind(results[position]!!)
    }

    class TrendingTVShowsVH(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(result: TrendingTVShows.Result) {
            view.tv_single_grid_title.text = result.name
            view.tv_single_rating.text = result.voteAverage.toString()
            view.iv_single_grid_poster.clipToOutline = true
            Picasso.get().load(ApiConstants.BASE_URL_IMAGE + result.posterPath ).into(view.iv_single_grid_poster)
        }
    }
}