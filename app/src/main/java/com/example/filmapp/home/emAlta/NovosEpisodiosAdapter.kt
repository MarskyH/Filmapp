package com.example.filmapp.home.emAlta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.TV.LatestTv
import com.example.filmapp.R
import com.squareup.picasso.Picasso

class NovosEpisodiosAdapter(val listener: onNovosEpisodiosItemClickListener): RecyclerView.Adapter<NovosEpisodiosAdapter.NovosEpisodiosViewHolder>() {

    var mediaList = arrayListOf<LatestTv>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NovosEpisodiosViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return NovosEpisodiosViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: NovosEpisodiosViewHolder,
        position: Int
    ) {
        val currentItem: LatestTv = mediaList[position]

        holder.mediaName.text = currentItem.original_name
        var url = "https://image.tmdb.org/t/p/w500" + currentItem.poster_path
        Picasso.get().load(url).into(holder.mediaImage)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    interface onNovosEpisodiosItemClickListener{
        fun novosEpisodiosItemClick(position: Int)
    }

    inner class NovosEpisodiosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val mediaName: TextView = itemView.findViewById(R.id.mediaName)
        val mediaImage: ImageView = itemView.findViewById(R.id.mediaImage)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.novosEpisodiosItemClick(position)
            }
        }

    }

    fun addList(item: LatestTv) {
        mediaList.add(item)
        notifyDataSetChanged()
    }

}