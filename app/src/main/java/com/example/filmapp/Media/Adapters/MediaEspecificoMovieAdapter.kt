package com.example.filmapp.Media.Adapters


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.Movie.SimilarMovies
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.squareup.picasso.Picasso


class MediaEspecificoMovieAdapter(private var listMediaEspecifico: SimilarMovies, val listener: OnMediaMovieClickListener, val Movie: Boolean): RecyclerView.Adapter<MediaEspecificoMovieAdapter.MediasViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MediasViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_media_especifica, parent, false)
        return MediasViewHolder(itemView)
    }

    override fun getItemCount() = listMediaEspecifico.results.size


    override fun onBindViewHolder(holder: MediasViewHolder, position: Int) {
       var movie = listMediaEspecifico.results[position]
        holder.tvMediaEspecifica.text = movie.title
        val picasso = Picasso.get()
        val pathImg = movie.poster_path
        val img = "${pathImg}".replace("http://","https://")
        picasso.load(img).into(holder.imgMediaEspecica)
        holder.imgMediaEspecica.setOnClickListener {
            val intent = Intent(holder.itemView.context, MediaSelectedActivity::class.java)
            if (movie != null){
                intent.putExtra("movie", Movie)
                intent.putExtra("media", movie)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    interface OnMediaMovieClickListener{
         fun MoviemediaClick(position: Int)

    }

    inner class MediasViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val tvMediaEspecifica: TextView = itemView.findViewById(R.id.tv_mediaEspecifica)
        val imgMediaEspecica: ImageView = itemView.findViewById(R.id.img_MediaEspecifica)

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