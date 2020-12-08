package com.example.filmapp.Home.Adapters.RecyclerViews

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Media
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_medialist.view.*

class DescubraMoviesAdapter(val listener: onDescubraMovieClickListener) :
    RecyclerView.Adapter<DescubraMoviesAdapter.DescubraListsViewHolder>() {

    var mediaList = arrayListOf<ResultMovie>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DescubraMoviesAdapter.DescubraListsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_medialist, parent, false)
        return DescubraListsViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: DescubraMoviesAdapter.DescubraListsViewHolder,
        position: Int
    ) {
        val currentItem: ResultMovie = mediaList[position]

        holder.mediaName.setText(currentItem.title)
        holder.mediaDetail1.setText(currentItem.release_date)
        holder.mediaDetail2.setText(currentItem.original_language)

//        holder.mediaImage.setImageResource(currentItem.mediaImage)

        holder.mediaName.setOnClickListener {
        }

    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    interface onDescubraMovieClickListener {
        fun descubraItemClick(position: Int)
    }

    inner class DescubraListsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val mediaName: TextView = itemView.findViewById(R.id.tv_mediaName_medialist)
        val mediaImage: ImageView = itemView.findViewById(R.id.iv_mediaImage_medialist)
        val mediaDetail1: TextView = itemView.findViewById(R.id.tv_mediaDetail1_medialist)
        val mediaDetail2: TextView = itemView.findViewById(R.id.tv_mediaDetail2_medialist)

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
