package com.example.filmapp.home.activitys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.R

class HistoricoAdapter(val listener: onHistoricoItemClickListener) :
    RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder>() {

    var mediaList = arrayListOf<ResultTv>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoricoViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_historicolist, parent, false)
        return HistoricoViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: HistoricoViewHolder,
        position: Int
    ) {
        val currentItem: ResultTv = mediaList[position]

        holder.mediaName.setText(currentItem.name)
//        holder.serieEpisode.setText(currentItem.serieEpisode)
        holder.mediaPreviewDate.text = "25/10/2020 - 23:32"
//        holder.mediaImage.setImageResource(currentItem.mediaImage)
//        holder.mediaType.setText(currentItem.mediaType)

    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    interface onHistoricoItemClickListener {
        fun historicoItemClick(position: Int)
    }

    inner class HistoricoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val mediaName: TextView = itemView.findViewById(R.id.tv_mediaName_historicoItem)
        val mediaImage: ImageView = itemView.findViewById(R.id.iv_mediaImage_historicoItem)
        val serieEpisode: TextView = itemView.findViewById(R.id.tv_serieEpisode_historicoItem)
        val mediaType: TextView = itemView.findViewById(R.id.tv_mediaType_historicoItem)
        val mediaPreviewDate: TextView =
            itemView.findViewById(R.id.tv_mediaPreviewDate_historicoItem)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.historicoItemClick(position)
            }
        }
    }

    fun addList(list: ArrayList<ResultTv>) {
        mediaList.addAll(list)
        notifyDataSetChanged()
    }
}