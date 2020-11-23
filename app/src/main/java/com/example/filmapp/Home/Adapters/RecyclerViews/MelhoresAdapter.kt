package com.example.filmapp.Home.Adapters.RecyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Media
import com.example.filmapp.R

class MelhoresAdapter(private val mediaList: ArrayList<Media>): RecyclerView.Adapter<MelhoresAdapter.MelhoresViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MelhoresAdapter.MelhoresViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.item_medialist, parent, false)
        return MelhoresViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MelhoresAdapter.MelhoresViewHolder,
        position: Int
    ) {
        val currentItem: Media = mediaList[position]

        holder.mediaName.setText(currentItem.mediaName)
        holder.mediaDetail1.setText(currentItem.mediaDetail1)
        holder.mediaDetail2.setText(currentItem.mediaDetail2)
        holder.mediaImage.setImageResource(currentItem.mediaImage)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    class MelhoresViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val mediaName: TextView = itemView.findViewById(R.id.tv_mediaName_medialist)
        val mediaImage: ImageView = itemView.findViewById(R.id.iv_mediaImage_medialist)
        val mediaDetail1: TextView = itemView.findViewById(R.id.tv_mediaDetail1_medialist)
        val mediaDetail2: TextView = itemView.findViewById(R.id.tv_mediaDetail2_medialist)

    }
}