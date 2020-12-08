package com.example.filmapp.Home.Adapters.RecyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Media
import com.example.filmapp.R

class EmCartazAdapter(val listener: onEmCartazItemClickListener): RecyclerView.Adapter<EmCartazAdapter.EmCartazViewHolder>() {

    var mediaList = arrayListOf<>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmCartazAdapter.EmCartazViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
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

    interface onEmCartazItemClickListener{
        fun emCartazItemClick(position: Int)
    }

    inner class EmCartazViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val mediaName: TextView = itemView.findViewById(R.id.mediaName)
        val mediaImage: ImageView = itemView.findViewById(R.id.mediaImage)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.emCartazItemClick(position)
            }
        }

    }

    fun addList(list: ArrayList<>) {
        mediaList.addAll(list)
        notifyDataSetChanged()
    }

}