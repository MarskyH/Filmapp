package com.example.filmapp.home.agenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Media
import com.example.filmapp.R
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeEntity
import com.example.filmapp.home.agenda.realtimeDatabase.AssistirMaisTardeScope
import com.squareup.picasso.Picasso

class AssistirMaisTardeAdapter(val listener: onAssistirMaisTardeItemClickListener) :
    RecyclerView.Adapter<AssistirMaisTardeAdapter.AssistirMaisTardeViewHolder>() {

    var mediaList = arrayListOf<AssistirMaisTardeScope>()
    var isClickable = true

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AssistirMaisTardeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return AssistirMaisTardeViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: AssistirMaisTardeViewHolder,
        position: Int
    ) {
        val currentItem: AssistirMaisTardeScope = mediaList[position]

        holder.mediaName.text = currentItem.title
        var url = "https://image.tmdb.org/t/p/w500" + currentItem.poster_path
        Picasso.get().load(url).into(holder.mediaImage)
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
            if(isClickable == true) {
                val position = adapterPosition
                if (RecyclerView.NO_POSITION != position) {
                    listener.assistirMaisTardeItemClick(position)
                }
            }
        }
    }

    fun addList(list: ArrayList<AssistirMaisTardeScope>) {
        mediaList = list
        notifyDataSetChanged()
    }

}