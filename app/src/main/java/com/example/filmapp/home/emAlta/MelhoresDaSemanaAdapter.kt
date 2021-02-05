package com.example.filmapp.home.emAlta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.All.ResultAll
import com.example.filmapp.R
import com.squareup.picasso.Picasso

class MelhoresDaSemanaAdapter(val listener: onMelhoresDaSemanaItemClickListener) :
    RecyclerView.Adapter<MelhoresDaSemanaAdapter.MelhoresDaSemanaViewHolder>() {

    var mediaList = arrayListOf<ResultAll>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MelhoresDaSemanaViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return MelhoresDaSemanaViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MelhoresDaSemanaViewHolder,
        position: Int
    ) {
        val currentItem: ResultAll = mediaList[position]

        if(currentItem.title == null){
            holder.mediaName.text = " "
        }else{
            holder.mediaName.text = currentItem.title
        }

        var url = "https://image.tmdb.org/t/p/w500" + currentItem.poster_path
        Picasso.get().load(url).into(holder.mediaImage)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    interface onMelhoresDaSemanaItemClickListener {
        fun melhoresDaSemanaItemClick(position: Int)
    }

    inner class MelhoresDaSemanaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
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

    fun addList(list: ArrayList<ResultAll>) {
        mediaList.addAll(list)
        notifyDataSetChanged()
    }

}