package com.example.filmapp.home.activitys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.R

class AcompanhandoAdapter(val listener: onAcompanhandoItemClickListener
) : RecyclerView.Adapter<AcompanhandoAdapter.AcompanhandoViewHolder>() {

    var mediaList = arrayListOf<ResultTv>()

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
        val currentItem: ResultTv = mediaList[position]

        holder.mediaName.setText(currentItem.name)
//        holder.serieEpisode.setText(currentItem.serieEpisode)
        holder.serieProgress.text = "89%"
//        holder.mediaImage.setImageResource(currentItem.mediaImage)
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

    fun addList(list: ArrayList<ResultTv>) {
        mediaList.addAll(list)
        notifyDataSetChanged()
    }

}