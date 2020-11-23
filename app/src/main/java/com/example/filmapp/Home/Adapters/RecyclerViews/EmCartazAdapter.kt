package com.example.filmapp.Home.Adapters.RecyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Media
import com.example.filmapp.R

class EmCartazAdapter(private val mediaList: ArrayList<Media>): RecyclerView.Adapter<EmCartazAdapter.EmCartazViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmCartazAdapter.EmCartazViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster_grande, parent, false)
        return EmCartazViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: EmCartazAdapter.EmCartazViewHolder,
        position: Int
    ) {
        val currentItem: Media = mediaList[position]

        holder.mediaName.setText(currentItem.mediaName)
        holder.mediaImage.setImageResource(currentItem.mediaImage)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    class EmCartazViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val mediaName: TextView = itemView.findViewById(R.id.mediaName_posterGrande)
        val mediaImage: ImageView = itemView.findViewById(R.id.mediaImage_posterGrande)

    }
}