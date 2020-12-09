package com.example.filmapp.Home.Adapters.RecyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Media
import com.example.filmapp.R

class ProximosAdapter(val listener: onProximosItemClickListener): RecyclerView.Adapter<ProximosAdapter.ProximosViewHolder>() {

    var mediaList = arrayListOf<Media>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProximosAdapter.ProximosViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.item_proximoslist_agenda, parent, false)
        return ProximosViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ProximosAdapter.ProximosViewHolder,
        position: Int
    ) {
        val currentItem: Media = mediaList[position]

        holder.mediaName.setText(currentItem.mediaName)
        holder.mediaLaunchSite.setText(currentItem.mediaLaunchSite)
        holder.mediaReleaseDate.setText(currentItem.mediaReleaseDate)
        holder.mediaType.setText(currentItem.mediaType)
        holder.serieEpisode.setText(currentItem.serieEpisode)
        holder.mediaImage.setImageResource(currentItem.mediaImage)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    interface onProximosItemClickListener{
        fun proximosItemClick(position: Int)
    }

    inner class ProximosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val mediaName: TextView = itemView.findViewById(R.id.tv_mediaName_proximosItem)
        val mediaImage: ImageView = itemView.findViewById(R.id.iv_mediaImage_proximosItem)
        val mediaLaunchSite: TextView = itemView.findViewById(R.id.tv_mediaLaunchSite_proximosItem)
        val mediaReleaseDate: TextView = itemView.findViewById(R.id.tv_mediaReleaseDate_proximosItem)
        val mediaType: TextView = itemView.findViewById(R.id.tv_mediaType_proximosItem)
        val serieEpisode: TextView = itemView.findViewById(R.id.tv_serieEpisode__proximosItem)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.proximosItemClick(position)
            }
        }
    }

    fun addList(list: ArrayList<Media>) {
        mediaList.addAll(list)
        notifyDataSetChanged()
    }

}