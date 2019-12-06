package com.dev.aman.movies_tmdb.ui.main.homeFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dev.aman.movies_tmdb.R
import com.dev.aman.movies_tmdb.api.data.TrendingMovies
import com.dev.aman.movies_tmdb.utils.ApiConstants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_single_grid.view.*

class TrendingMoviesAdapter(var results: List<TrendingMovies.Result?>)
    : RecyclerView.Adapter<TrendingMoviesAdapter.TrendingMoviesVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMoviesVH {
        return TrendingMoviesVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_single_grid,
                parent,
                false))
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: TrendingMoviesVH, position: Int) {
        holder.bind(results[position]!!)
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