package com.example.filmapp.Home.Adapters.RecyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Media
import com.example.filmapp.R
import kotlinx.android.synthetic.main.item_medialist.view.*

class DescubraListsAdapter(
    private val mediaList: ArrayList<Media>,
    val listener: onDescubraItemClickListener
) : RecyclerView.Adapter<DescubraListsAdapter.DescubraListsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DescubraListsAdapter.DescubraListsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_medialist, parent, false)
        return DescubraListsViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: DescubraListsAdapter.DescubraListsViewHolder,
        position: Int
    ) {
        val currentItem: Media = mediaList[position]

        holder.mediaName.setText(currentItem.mediaName)
        holder.mediaDetail1.setText(currentItem.mediaDetail1)
        holder.mediaDetail2.setText(currentItem.mediaDetail2)
        holder.mediaImage.setImageResource(currentItem.mediaImage)

    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    interface onDescubraItemClickListener {
        fun descubraItemClick(position: Int)
//        fun assistirMaisTardeIndicationClick(position: Int)
//        fun evaluationIndicationClick(position: Int)
//        fun shareIndicationIndicationClick(position: Int)
    }

    inner class DescubraListsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val mediaName: TextView = itemView.findViewById(R.id.tv_mediaName_medialist)
        val mediaImage: ImageView = itemView.findViewById(R.id.iv_mediaImage_medialist)
        val mediaDetail1: TextView = itemView.findViewById(R.id.tv_mediaDetail1_medialist)
        val mediaDetail2: TextView = itemView.findViewById(R.id.tv_mediaDetail2_medialist)

//        init {
//            itemView.setOnClickListener {
//                fun onClick(v: View?) {
//                    val position = adapterPosition
//                    if (RecyclerView.NO_POSITION != position) {
//                        listener.descubraItemClick(position)
//                    }
//                }
//            }
//
//            itemView.assistirMaisTardeIndication_medialist.setOnClickListener {
//                fun onClick(v: View?) {
//                    val position = adapterPosition
//                    if (RecyclerView.NO_POSITION != position) {
//                        listener.assistirMaisTardeIndicationClick(position)
//                    }
//                }
//            }
//
//            itemView.evaluationIndication_medialist.setOnClickListener {
//                fun onClick(v: View?) {
//                    val position = adapterPosition
//                    if (RecyclerView.NO_POSITION != position) {
//                        listener.evaluationIndicationClick(position)
//                    }
//                }
//            }
//
//            itemView.shareIndication_medialist.setOnClickListener {
//                 fun onClick(v: View?) {
//                    val position = adapterPosition
//                    if (RecyclerView.NO_POSITION != position) {
//                        listener.shareIndicationIndicationClick(position)
//                    }
//                }
//            }
//        }
//
//        override fun onClick(v: View?) {

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
    }
