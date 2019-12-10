package com.dev.aman.movies_tmdb.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dev.aman.movies_tmdb.R
import com.dev.aman.movies_tmdb.data.model.UpcomingMovies
import com.dev.aman.movies_tmdb.utils.ApiConstants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_single_grid.view.*

class UpcomingMoviesAdapter(var results: List<UpcomingMovies.Result?>)
    : RecyclerView.Adapter<UpcomingMoviesAdapter.UpcomingMoviesVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMoviesVH {
        return UpcomingMoviesVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_single_grid,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: UpcomingMoviesVH, position: Int) {
        holder.bind(results[position]!!)
    }

    class UpcomingMoviesVH(private val view: View): RecyclerView.ViewHolder(view) {
        fun  bind(result: UpcomingMovies.Result) {
            view.tv_single_grid_title.text = result.title
            view.tv_single_rating.visibility = View.GONE
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