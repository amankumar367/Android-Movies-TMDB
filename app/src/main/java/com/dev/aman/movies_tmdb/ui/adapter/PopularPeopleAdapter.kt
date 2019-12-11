package com.dev.aman.movies_tmdb.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.aman.movies_tmdb.R
import com.dev.aman.movies_tmdb.data.model.PopularPeople
import com.dev.aman.movies_tmdb.utils.ApiConstants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_single_popular_people.view.*

class PopularPeopleAdapter(var results: List<PopularPeople.Result?>)
    : RecyclerView.Adapter<PopularPeopleAdapter.PopularPeopleVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularPeopleVH {
        return PopularPeopleVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_single_popular_people,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: PopularPeopleVH, position: Int) {
        holder.bind(results[position]!!)
    }

    class PopularPeopleVH(private val view: View): RecyclerView.ViewHolder(view) {

        fun bind(result: PopularPeople.Result) {
            view.tv_popular_people_name.text = result.name!!
            Picasso.get().load(ApiConstants.BASE_URL_IMAGE + result.profilePath).into(view.circleImageView_popular_peole)
        }
    }

}
