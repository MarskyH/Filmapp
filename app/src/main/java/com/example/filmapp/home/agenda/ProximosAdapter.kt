package com.example.filmapp.home.agenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Media
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.R
import com.squareup.picasso.Picasso

class ProximosAdapter(val listener: onProximosItemClickListener): RecyclerView.Adapter<ProximosAdapter.ProximosViewHolder>() {

    var mediaList = arrayListOf<TvDetails>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProximosViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.item_proximoslist_agenda, parent, false)
        return ProximosViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ProximosViewHolder,
        position: Int
    ) {
        val currentItem: TvDetails = mediaList[position]

        var url = "https://image.tmdb.org/t/p/w500" + currentItem.poster_path
        Picasso.get().load(url).into(holder.mediaImage)

        holder.mediaName.setText(currentItem.formattedName)

        var seasonNumber = currentItem.next_episode_to_air.season_number.toString()
        var episodeNumber = currentItem.next_episode_to_air.episode_number. toString()
        var episodeName = currentItem.next_episode_to_air.formattedNameEpisode

        if(episodeName.length != 0)
            holder.serieEpisode.setText("Episódio: " + episodeNumber + " ( " + episodeName + " )")
        else
            holder.serieEpisode.setText("Episódio: " + episodeNumber)

        holder.serieSeason.setText("Temporada: " + seasonNumber)

        holder.mediaLaunchSite.setText("Onde: " + currentItem.networks.get(0).name)

        holder.mediaReleaseDate.setText("Quando: " + currentItem.next_episode_to_air.air_date)

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
        val serieEpisode: TextView = itemView.findViewById(R.id.tv_serieEpisode_proximosItem)
        val serieSeason: TextView = itemView.findViewById(R.id.tv_serieSeason_proximosItem)

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

    fun addList(item: TvDetails) {
        mediaList.add(item)
        notifyDataSetChanged()
    }

}