package com.example.filmapp.home.acompanhando

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.R
import com.example.filmapp.home.acompanhando.dataBase.AcompanhandoEntity
import com.squareup.picasso.Picasso

class AcompanhandoAdapter(val listener: onAcompanhandoItemClickListener
) : RecyclerView.Adapter<AcompanhandoAdapter.AcompanhandoViewHolder>() {

    var mediaList = listOf<AcompanhandoEntity>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AcompanhandoViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_acompanhandolist_acompanhando, parent, false)
        return AcompanhandoViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: AcompanhandoViewHolder,
        position: Int
    ) {
        val currentItem: AcompanhandoEntity = mediaList[position]

        holder.mediaName.setText(currentItem.title)

        var url = "https://image.tmdb.org/t/p/w500" + currentItem.poster_path
        Picasso.get().load(url).into(holder.mediaImage)

        if (currentItem.finished == true){
            holder.subtitle.setText("Finalizada")
            holder.serieEpisode.setText(" ")
        }else {

            var season = currentItem.currentSeason.toString()

            var episodeNumber = if (currentItem.nextEpisodeNumber < 10){
                 "0" + currentItem.nextEpisodeNumber.toString()
            }else{
                currentItem.nextEpisodeNumber.toString()
            }

            var episodeTitle = currentItem.nextEpisodeTitle

            if((episodeTitle == null) || (episodeTitle.length == 0)) {
                holder.serieEpisode.setText(season + "x" + episodeNumber)
            }else{
                holder.serieEpisode.setText(season + "x" + episodeNumber + " - " + episodeTitle)
            }

        }

        holder.serieProgress.setText(currentItem.userProgress.toString() + "%")
        holder.progressBar.progress = currentItem.userProgress.toInt()

    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    interface onAcompanhandoItemClickListener {
        fun AcompanhandoItemClick(position: Int)
    }

    inner class AcompanhandoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val mediaName: TextView = itemView.findViewById(R.id.tv_mediaName_acompanhandoItem)
        val mediaImage: ImageView = itemView.findViewById(R.id.iv_mediaImage_acompanhandoItem)
        val serieEpisode: TextView = itemView.findViewById(R.id.tv_serieEpisode_acompanhandoItem)
        val serieProgress: TextView = itemView.findViewById(R.id.tv_serieProgress_acompanhandoItem)
        val subtitle: TextView = itemView.findViewById(R.id.tv_subtitle)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar_serieProgress_acompanhandoItem)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.AcompanhandoItemClick(position)
            }
        }
    }

    fun addList(list: List<AcompanhandoEntity>) {
        mediaList = list
        notifyDataSetChanged()
    }

}