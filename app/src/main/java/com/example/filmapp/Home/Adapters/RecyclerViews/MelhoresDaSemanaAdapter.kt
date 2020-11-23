package com.example.filmapp.Home.Adapters.RecyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Media
import com.example.filmapp.R

class MelhoresDaSemanaAdapter(private val mediaList: ArrayList<Media>): RecyclerView.Adapter<MelhoresDaSemanaAdapter.MelhoresDaSemanaViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MelhoresDaSemanaAdapter.MelhoresDaSemanaViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster_grande, parent, false)
        return MelhoresDaSemanaViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MelhoresDaSemanaAdapter.MelhoresDaSemanaViewHolder,
        position: Int
    ) {
        val currentItem: Media = mediaList[position]

        holder.mediaName.setText(currentItem.mediaName)
        holder.mediaImage.setImageResource(currentItem.mediaImage)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    class MelhoresDaSemanaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val mediaName: TextView = itemView.findViewById(R.id.mediaName_posterGrande)
        val mediaImage: ImageView = itemView.findViewById(R.id.mediaImage_posterGrande)

    }
}