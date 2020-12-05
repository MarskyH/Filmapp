package com.example.filmapp.Media.Adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R

class HomeMediaAdapter (private var listMedias: ArrayList<com.example.filmapp.Classes.Media>, val listener: OnHomeMediaClickListener, val Movie: Boolean): RecyclerView.Adapter<HomeMediaAdapter.HomeMediasViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeMediasViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return HomeMediasViewHolder(itemView)
    }

    override fun getItemCount() = listMedias.size


    override fun onBindViewHolder(holder: HomeMediasViewHolder, position: Int) {
        var homeMedia = listMedias.get(position)
        holder.img.setImageResource(homeMedia.mediaImage)
        holder.titulo.text = homeMedia.mediaName
        holder.img.setOnClickListener {
            val intent = Intent(holder.itemView.context, MediaSelectedActivity::class.java)
            var bundle = Bundle()
            bundle.putInt("imagem", homeMedia.mediaImage)
            bundle.putString("sinopse", homeMedia.mediaSinopse)
            bundle.putBoolean("movie?", Movie)
            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)
        }

    }

    interface OnHomeMediaClickListener{
        fun homeMediaClick(position: Int)

    }

    inner class HomeMediasViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val img: ImageView = itemView.findViewById(R.id.mediaImage)
        val titulo: TextView = itemView.findViewById(R.id.mediaName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = adapterPosition
            if(RecyclerView.NO_POSITION != position){
                listener.homeMediaClick(position)
            }
        }
    }
}