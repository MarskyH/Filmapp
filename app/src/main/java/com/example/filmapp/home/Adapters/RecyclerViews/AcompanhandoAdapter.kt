package com.example.filmapp.Home.Adapters.RecyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Media
import com.example.filmapp.R

class AcompanhandoAdapter(private val mediaList: ArrayList<Media>): RecyclerView.Adapter<AcompanhandoAdapter.AcompanhandoViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AcompanhandoAdapter.AcompanhandoViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.item_acompanhandolist_acompanhando, parent, false)
        return AcompanhandoViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: AcompanhandoAdapter.AcompanhandoViewHolder,
        position: Int
    ) {
        val currentItem: Media = mediaList[position]

        holder.mediaName.setText(currentItem.mediaName)
        holder.serieEpisode.setText(currentItem.serieEpisode)
        holder.serieProgress.text = "89%"
        holder.mediaImage.setImageResource(currentItem.mediaImage)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    class AcompanhandoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val mediaName: TextView = itemView.findViewById(R.id.tv_mediaName_acompanhandoItem)
        val mediaImage: ImageView = itemView.findViewById(R.id.iv_mediaImage_acompanhandoItem)
        val serieEpisode: TextView = itemView.findViewById(R.id.tv_serieEpisode_acompanhandoItem)
        val serieProgress: TextView = itemView.findViewById(R.id.tv_serieProgress_acompanhandoItem)

    }
}