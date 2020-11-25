package com.example.filmapp.Home.Adapters.RecyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Media
import com.example.filmapp.R

class MelhoresDaSemanaAdapter(private val mediaList: ArrayList<Media>, val listener: onMelhoresDaSemanaItemClickListener): RecyclerView.Adapter<MelhoresDaSemanaAdapter.MelhoresDaSemanaViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MelhoresDaSemanaAdapter.MelhoresDaSemanaViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return MelhoresDaSemanaViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MelhoresDaSemanaAdapter.MelhoresDaSemanaViewHolder,
        position: Int
    ) {
        val currentItem: Media = mediaList[position]

        holder.mediaName.setText(currentItem.mediaName)
        holder.mediaImage.setImageResource(currentItem.mediaImage)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    interface onMelhoresDaSemanaItemClickListener{
        fun melhoresDaSemanaItemClick(position: Int)
    }

    inner class MelhoresDaSemanaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val mediaName: TextView = itemView.findViewById(R.id.mediaName)
        val mediaImage: ImageView = itemView.findViewById(R.id.mediaImage)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.melhoresDaSemanaItemClick(position)
            }
        }
    }
}