package com.example.filmapp.Home.Adapters.RecyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Media
import com.example.filmapp.R

class AssistirMaisTardeAdapter(val listener: onAssistirMaisTardeItemClickListener) :
    RecyclerView.Adapter<AssistirMaisTardeAdapter.AssistirMaisTardeViewHolder>() {

    var mediaList = arrayListOf<>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AssistirMaisTardeAdapter.AssistirMaisTardeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return AssistirMaisTardeViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: AssistirMaisTardeAdapter.AssistirMaisTardeViewHolder,
        position: Int
    ) {
        val currentItem: Media = mediaList[position]

        holder.mediaName.setText(currentItem.mediaName)
        holder.mediaImage.setImageResource(currentItem.mediaImage)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    interface onAssistirMaisTardeItemClickListener {
        fun assistirMaisTardeItemClick(position: Int)
    }

    inner class AssistirMaisTardeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val mediaName: TextView = itemView.findViewById(R.id.mediaName)
        val mediaImage: ImageView = itemView.findViewById(R.id.mediaImage)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.assistirMaisTardeItemClick(position)
            }
        }
    }

    fun addList(list: ArrayList<>) {
        mediaList.addAll(list)
        notifyDataSetChanged()
    }

}