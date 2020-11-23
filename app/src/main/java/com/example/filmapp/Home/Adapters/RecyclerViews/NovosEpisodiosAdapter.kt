package com.example.filmapp.Home.Adapters.RecyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Media
import com.example.filmapp.R

class NovosEpisodiosAdapter(private val mediaList: ArrayList<Media>): RecyclerView.Adapter<NovosEpisodiosAdapter.NovosEpisodiosViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NovosEpisodiosAdapter.NovosEpisodiosViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return NovosEpisodiosViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: NovosEpisodiosAdapter.NovosEpisodiosViewHolder,
        position: Int
    ) {
        val currentItem: Media = mediaList[position]

        holder.mediaName.setText(currentItem.mediaName)
        holder.mediaImage.setImageResource(currentItem.mediaImage)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    class NovosEpisodiosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val mediaName: TextView = itemView.findViewById(R.id.mediaName)
        val mediaImage: ImageView = itemView.findViewById(R.id.mediaImage)

    }
}