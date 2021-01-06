package com.example.filmapp.Media.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.squareup.picasso.Picasso

class HomeMediaMovieAdapter(
    private var listMediaMovie: ArrayList<ResultMovie>,
    val listener: OnHomeMediaMovieClickListener,
    val Movie: Boolean?, val config: Config) : RecyclerView.Adapter<HomeMediaMovieAdapter.HomeMediasMovieViewHolder>() {
    val picasso = Picasso.get()

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
                intent.putExtra("sinopse", homeMovie.overview)
                intent.putExtra("id", homeMovie.id)
                holder.itemView.context.startActivity(intent)
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