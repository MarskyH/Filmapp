package com.example.filmapp.Media.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.Movie.ImagesMovie
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.squareup.picasso.Picasso


class ResourceMovieAdapter(private var listImagesMovie: ImagesMovie, val listener: OnMideaMovieClickListener, val config: Config): RecyclerView.Adapter<ResourceMovieAdapter.MideasViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MideasViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_media_medias, parent, false)
        return MideasViewHolder(itemView)
    }

    override fun getItemCount() = listImagesMovie.backdrops.size


    override fun onBindViewHolder(holder: MideasViewHolder, position: Int) {
       var movie = listImagesMovie.backdrops.get(position)
        if (movie != null){
            val picasso = Picasso.get()
            val baseURl = config.images.secure_base_url
            val size = "original"
            val pathImg = movie.file_path
            val img = "${baseURl}${size}${pathImg}".replace("http://","https://")
            picasso.load(img).into(holder.imgMidea)
        }

    }

    interface OnMideaMovieClickListener{
         fun MoviemideaClick(position: Int)

    }

    inner class MideasViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val imgMidea: ImageView = itemView.findViewById(R.id.img_MediaMedias)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = adapterPosition
            if(RecyclerView.NO_POSITION != position){
                listener.MoviemideaClick(position)
            }
        }
    }
}