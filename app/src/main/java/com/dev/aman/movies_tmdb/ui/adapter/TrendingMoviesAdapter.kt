package com.dev.aman.movies_tmdb.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.aman.movies_tmdb.R
import com.dev.aman.movies_tmdb.data.model.TrendingMovies
import com.dev.aman.movies_tmdb.utils.ApiConstants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_single_grid.view.*

class TrendingMoviesAdapter
    : PagedListAdapter<TrendingMovies.Result, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TrendingMoviesVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_single_grid,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val result = getItem(position)
        result?.let {
            (holder as TrendingMoviesVH).bind(result)
        }
    }

    companion object {
        private val COMPARATOR
                = object : DiffUtil.ItemCallback<TrendingMovies.Result>() {
            override fun areItemsTheSame(oldItem: TrendingMovies.Result,
                                         newItem: TrendingMovies.Result)
                    : Boolean = oldItem.id!! == newItem.id!!

            override fun areContentsTheSame(oldItem: TrendingMovies.Result,
                                            newItem: TrendingMovies.Result)
                    : Boolean = oldItem == newItem
        }
    }


    class TrendingMoviesVH(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(result: TrendingMovies.Result) {
            view.tv_single_grid_title.text = result.title
            view.tv_single_rating.text = result.voteAverage.toString()
            view.iv_single_grid_poster.clipToOutline = true
            Picasso.get().load(ApiConstants.BASE_URL_IMAGE + result.posterPath ).into(view.iv_single_grid_poster)
            onClick()
        }

        private fun onClick() {
            view.iv_vertical_three_dot.setOnClickListener{
                Toast.makeText(view.context, "Three DOT", Toast.LENGTH_SHORT).show()
            }
        }
    }
}