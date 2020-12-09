package com.example.filmapp.home.FragRecyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.R

class DescubraTVAdapter(val listener: onDescubraTVClickListener) :
    RecyclerView.Adapter<DescubraTVAdapter.DescubraListsViewHolder>() {

    var mediaList = arrayListOf<ResultTv>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DescubraListsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_medialist, parent, false)
        return DescubraListsViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: DescubraListsViewHolder,
        position: Int
    ) {
        val currentItem: ResultTv = mediaList[position]

        holder.mediaName.setText(currentItem.name)
        holder.mediaDetail1.setText(currentItem.first_air_date)
        holder.mediaDetail2.setText(currentItem.original_language)

//        holder.mediaImage.setImageResource(currentItem.mediaImage)

        holder.mediaName.setOnClickListener {
        }

    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    interface onDescubraTVClickListener {
        fun descubraItemClick(position: Int)
    }

    inner class DescubraListsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val mediaName: TextView = itemView.findViewById(R.id.tv_mediaName_medialist)
        val mediaImage: ImageView = itemView.findViewById(R.id.iv_mediaImage_medialist)
        val mediaDetail1: TextView = itemView.findViewById(R.id.tv_mediaDetail1_medialist)
        val mediaDetail2: TextView = itemView.findViewById(R.id.tv_mediaDetail2_medialist)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.descubraItemClick(position)
            }
        }

    }

    fun addList(list: ArrayList<ResultTv>) {
        mediaList.addAll(list)
        notifyDataSetChanged()
    }
}
