package com.example.filmapp.home.descubra

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.All.ResultSearchAll
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.Movie.SearchMovie
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.squareup.picasso.Picasso

class DescubraMovieAdapter(
    private var listMediaMovie: SearchMovie,
    val listener: DescubraMovieClickListener,
    val Movie: Boolean?, val config: Config) : RecyclerView.Adapter<DescubraMovieAdapter.DescubraMovieViewHolder>() {
    val picasso = Picasso.get()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DescubraMovieViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return DescubraMovieViewHolder(itemView)
    }

    override fun getItemCount() = listMediaMovie.results.size

    override fun onBindViewHolder(holder: DescubraMovieViewHolder, position: Int) {
            var homeMovie = listMediaMovie.results[position]
            var baseURl = config.images.secure_base_url
            var size = "original"
            val pathImg = homeMovie.poster_path
            val img = "${baseURl}${size}${pathImg}".replace("http://","https://")
            picasso.load(img).into(holder.img)
            holder.titulo.text = homeMovie.title
            holder.img.setOnClickListener {
                val intent = Intent(holder.itemView.context, MediaSelectedActivity::class.java)
                intent.putExtra("poster", img)
                intent.putExtra("movie", Movie)
                intent.putExtra("mediaMovie", homeMovie)
                holder.itemView.context.startActivity(intent)
            }
        }


    interface DescubraMovieClickListener {
        fun descubraMovieClick(position: Int)

    }

    inner class DescubraMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val img: ImageView = itemView.findViewById(R.id.mediaImage)
        val titulo: TextView = itemView.findViewById(R.id.mediaName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.descubraMovieClick(position)
            }
        }
    }
}