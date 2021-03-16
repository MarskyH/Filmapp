package com.example.filmapp.home.descubra

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.R
import com.squareup.picasso.Picasso

class DescubraSeriesAdapter(val listener: onDescubraSerieClickListener) :
    RecyclerView.Adapter<DescubraSeriesAdapter.DescubraSerieListsViewHolder>() {

    var mediaList = arrayListOf<ResultTv>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DescubraSerieListsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return DescubraSerieListsViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: DescubraSerieListsViewHolder,
        position: Int
    ) {
        val currentItem: ResultTv = mediaList[position]

        holder.mediaName.text = currentItem.original_name

        var url = "https://image.tmdb.org/t/p/w500" + currentItem.poster_path
        Picasso.get().load(url).into(holder.mediaImage)

    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    interface onDescubraSerieClickListener {
        fun descubraItemClick(position: Int)
    }

    inner class DescubraSerieListsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
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

    fun addList(list: ArrayList<ResultTv>) {
        mediaList.addAll(list)
        notifyDataSetChanged()
    }
}
