package com.example.filmapp.Media.Adapters


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.APIConfig.ExcpetionTitle
import com.example.filmapp.Entities.APIConfig.URL_IMAGE
import com.example.filmapp.Entities.Movie.SimilarMovies
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.squareup.picasso.Picasso


class MediaEspecificoMovieAdapter(private var listMediaEspecifico: SimilarMovies, 
                                  val listener: OnMediaMovieClickListener, 
                                  val Movie: Boolean?, 
                                  val config: Config) : RecyclerView.Adapter<MediaEspecificoMovieAdapter.MediasViewHolder>() {
    val picasso = Picasso.get()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MediasViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_poster_grande, parent, false)
        return MediasViewHolder(itemView)
    }

    override fun getItemCount() = listMediaEspecifico.results.size


    override fun onBindViewHolder(holder: MediasViewHolder, position: Int) {
        val movie = listMediaEspecifico.results.get(position)
        if (movie.poster_path != "" && movie.poster_path != null) {
            picasso.load(URL_IMAGE + movie.poster_path).into(holder.imgMediaEspecica)
        }else
            picasso.load(R.drawable.sem_imagem).into(holder.imgMediaEspecica)
        if(movie.title != "" && movie.title != null){
            holder.tvMediaEspecifica.text = movie.title
        }else{
            holder.tvMediaEspecifica.text = ExcpetionTitle
        }
    }

    interface OnMediaMovieClickListener{
         fun MoviemediaClick(position: Int)

    }

    inner class MediasViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val tvMediaEspecifica: TextView = itemView.findViewById(R.id.mediaName)
        val imgMediaEspecica: ImageView = itemView.findViewById(R.id.mediaImage)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = adapterPosition
            if(RecyclerView.NO_POSITION != position){
                listener.MoviemediaClick(position)
            }
        }
    }
}