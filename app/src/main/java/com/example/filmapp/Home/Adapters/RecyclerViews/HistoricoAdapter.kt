package com.example.filmapp.Home.Adapters.RecyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Media
import com.example.filmapp.R

class HistoricoAdapter(private val mediaList: ArrayList<Media>): RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoricoAdapter.HistoricoViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.item_historicolist, parent, false)
        return HistoricoViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: HistoricoAdapter.HistoricoViewHolder,
        position: Int
    ) {
        val currentItem: Media = mediaList[position]

        holder.mediaName.setText(currentItem.mediaName)
        holder.serieEpisode.setText(currentItem.serieEpisode)
        holder.mediaPreviewDate.text = "25/10/2020 - 23:32"
        holder.mediaImage.setImageResource(currentItem.mediaImage)
        holder.mediaType.setText(currentItem.mediaType)

    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    class HistoricoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val mediaName: TextView = itemView.findViewById(R.id.tv_mediaName_historicoItem)
        val mediaImage: ImageView = itemView.findViewById(R.id.iv_mediaImage_historicoItem)
        val serieEpisode: TextView = itemView.findViewById(R.id.tv_serieEpisode_historicoItem)
        val mediaType: TextView = itemView.findViewById(R.id.tv_mediaType_historicoItem)
        val mediaPreviewDate: TextView = itemView.findViewById(R.id.tv_mediaPreviewDate_historicoItem)

    }
}