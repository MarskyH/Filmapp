package com.example.filmapp.Media.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.Movie.ImagesMovie
import com.example.filmapp.Entities.TV.ImagesTv
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.squareup.picasso.Picasso


class ResourceSerieAdapter(private var listImagesSerie: ImagesTv, val listener: OnMideaSerieClickListener): RecyclerView.Adapter<ResourceSerieAdapter.MideasViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MideasViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_media_medias, parent, false)
        return MideasViewHolder(itemView)
    }

    override fun getItemCount() = listImagesSerie.backdrops.size


    override fun onBindViewHolder(holder: MideasViewHolder, position: Int) {
       var movie = listImagesSerie.backdrops.get(position)
        val picasso = Picasso.get()
        val pathImg = movie.file_path
        val img = "${pathImg}".replace("http://","https://")
        picasso.load(img).into(holder.imgMidea)
    }

    interface OnMideaSerieClickListener{
         fun SeriemideaClick(position: Int)

    }

    inner class MideasViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val imgMidea: ImageView = itemView.findViewById(R.id.img_MediaMedias)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = adapterPosition
            if(RecyclerView.NO_POSITION != position){
                listener.SeriemideaClick(position)
            }
        }
    }
}