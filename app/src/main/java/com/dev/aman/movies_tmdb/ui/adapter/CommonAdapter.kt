package com.dev.aman.movies_tmdb.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.aman.movies_tmdb.R
import com.dev.aman.movies_tmdb.data.model.Result
import com.dev.aman.movies_tmdb.extentions.gone
import com.dev.aman.movies_tmdb.network.RequestType
import com.dev.aman.movies_tmdb.utils.ApiConstants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_single_grid.view.*
import kotlinx.android.synthetic.main.layout_single_popular_people.view.*

class CommonAdapter(private val type: RequestType)
    : PagedListAdapter<Result, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            RequestType.POPULAR_PEOPLES -> {
                PopularPeopleVH(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.layout_single_popular_people,
                        parent,
                        false
                    )
                )
            }
            else -> {
                CommonViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.layout_single_grid,
                        parent,
                        false
                    ),
                    type
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val result = getItem(position)
        result?.let {
            when (type) {
                RequestType.POPULAR_PEOPLES -> (holder as PopularPeopleVH).bind(result)
                else -> (holder as CommonViewHolder).bind(result)
            }
        }
    }

    companion object {
        private val COMPARATOR
                = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result,
                                         newItem: Result)
                    : Boolean = oldItem.id!! == newItem.id!!

            override fun areContentsTheSame(oldItem: Result,
                                            newItem: Result)
                    : Boolean = oldItem == newItem
        }
    }


    class CommonViewHolder(
        private val view: View,
        private val type: RequestType
    ) : RecyclerView.ViewHolder(view) {
        fun bind(result: Result) {
            view.tv_single_grid_title.text = result.title?.let { it } ?: run { result.name }
            view.tv_single_rating.text = result.voteAverage.toString()
            view.iv_single_grid_poster.clipToOutline = true
            Picasso.get().load(ApiConstants.BASE_URL_IMAGE + result.posterPath ).into(view.iv_single_grid_poster)

            if (type == RequestType.UPCOMING_MOVIES) view.tv_single_rating.gone()

            onClick()
        }

        private fun onClick() {
            view.setOnClickListener{

            }
        }
    }

    class PopularPeopleVH(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(result: Result) {
            view.tv_popular_people_name.text = result.name!!
            Picasso.get().load(ApiConstants.BASE_URL_IMAGE + result.profilePath).into(view.circleImageView_popular_peole)
            onClick()
        }

        private fun onClick() {
            view.setOnClickListener{

            }
        }
    }
}