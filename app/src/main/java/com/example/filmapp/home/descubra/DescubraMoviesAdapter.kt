package com.example.filmapp.home.descubra

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.R
import com.squareup.picasso.Picasso

class DescubraMoviesAdapter(val listener: onDescubraMovieClickListener) :
    RecyclerView.Adapter<DescubraMoviesAdapter.DescubraListsViewHolder>() {

    var mediaList = arrayListOf<ResultMovie>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DescubraListsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return DescubraListsViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: DescubraListsViewHolder,
        position: Int
    ) {
        val currentItem: ResultMovie = mediaList[position]

        holder.mediaName.text = currentItem.title

        var url = "https://image.tmdb.org/t/p/w500" + currentItem.poster_path
        Picasso.get().load(url).into(holder.mediaImage)

    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    interface onDescubraMovieClickListener {
        fun descubraItemClick(position: Int)
    }

    inner class DescubraListsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val mediaName: TextView = itemView.findViewById(R.id.mediaName)
        val mediaImage: ImageView = itemView.findViewById(R.id.mediaImage)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.descubraItemClick(position)
            }
        }

    }

    fun addList(list: ArrayList<ResultMovie>) {
        mediaList.addAll(list)
        notifyDataSetChanged()
    }
}
