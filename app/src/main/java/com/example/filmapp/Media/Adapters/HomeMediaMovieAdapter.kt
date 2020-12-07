package com.example.filmapp.Media.Adapters

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.squareup.picasso.Picasso

class HomeMediaMovieAdapter(
    private var listMediaMovie: ArrayList<ResultMovie>,
    val listener: OnHomeMediaMovieClickListener,
    val Movie: Boolean) : RecyclerView.Adapter<HomeMediaMovieAdapter.HomeMediasMovieViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeMediasMovieViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return HomeMediasMovieViewHolder(itemView)
    }


    override fun getItemCount() = listMediaMovie.size


    override fun onBindViewHolder(holder: HomeMediasMovieViewHolder, position: Int) {
        var homeMovie = listMediaMovie[position]
        holder.titulo.text = homeMovie.title
        val picasso = Picasso.get()
        val pathImg = homeMovie.poster_path
        val img = "${pathImg}".replace("http://","https://")
        picasso.load(img).into(holder.img)
        holder.img.setOnClickListener {
            val intent = Intent(holder.itemView.context, MediaSelectedActivity::class.java)
            if (homeMovie != null){
                intent.putExtra("movie", Movie)
                intent.putExtra("media", homeMovie)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    interface OnHomeMediaMovieClickListener {
        fun homeMediaMovieClick(position: Int)

    }

    inner class HomeMediasMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val img: ImageView = itemView.findViewById(R.id.mediaImage)
        val titulo: TextView = itemView.findViewById(R.id.mediaName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.homeMediaMovieClick(position)
            }
        }
    }
}