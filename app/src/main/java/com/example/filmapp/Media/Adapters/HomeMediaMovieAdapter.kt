package com.example.filmapp.Media.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.APIConfig.ExcpetionTitle
import com.example.filmapp.Entities.APIConfig.URL_IMAGE
import com.example.filmapp.Entities.Movie.ResultMovie
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
        val homeMovie = listMediaMovie[position]
        if (homeMovie.poster_path != "" && homeMovie.poster_path != null) {
            picasso.load(URL_IMAGE + homeMovie.poster_path).into(holder.img)
        }else
            picasso.load(R.drawable.sem_imagem).into(holder.img)
        if(homeMovie.title != "" && homeMovie.title != null){
            holder.titulo.text = homeMovie.title
        }else{
            holder.titulo.text = ExcpetionTitle
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