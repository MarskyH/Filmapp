package com.example.filmapp.Filmes.Adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Filmes.UI.FilmeSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Series.Classes.Media
import com.example.filmapp.Series.Classes.Serie
import com.example.filmapp.Series.Ui.SerieSelectedActivity


class HomeFilmesAdapter(private var listMedias: ArrayList<com.example.filmapp.Classes.Media>, val listener: OnHomeFilmeClickListener): RecyclerView.Adapter<HomeFilmesAdapter.HomeFilmesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeFilmesViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return HomeFilmesViewHolder(itemView)
    }

    override fun getItemCount() = listMedias.size


    override fun onBindViewHolder(holder: HomeFilmesViewHolder, position: Int) {
       var homeMedia = listMedias.get(position)
        holder.img.setImageResource(homeMedia.mediaImage)
        holder.titulo.text = homeMedia.mediaName
        holder.img.setOnClickListener {
            val intent = Intent(holder.itemView.context, FilmeSelectedActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }


    }

    interface OnHomeFilmeClickListener{
         fun homeFilmeClick(position: Int)

    }

    inner class HomeFilmesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val img: ImageView = itemView.findViewById(R.id.mediaImage)
        val titulo: TextView = itemView.findViewById(R.id.mediaName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = adapterPosition
            if(RecyclerView.NO_POSITION != position){
                listener.homeFilmeClick(position)
            }
        }
    }
}