package com.example.filmapp.Media.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.R


class MediaAdapter(private var listMedia: ArrayList<com.example.filmapp.Classes.Media>, val listener: OnMideaClickListener): RecyclerView.Adapter<MediaAdapter.MideasViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MideasViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_media_medias, parent, false)
        return MideasViewHolder(itemView)
    }

    override fun getItemCount() = listMedia.size


    override fun onBindViewHolder(holder: MideasViewHolder, position: Int) {
       var media = listMedia.get(position)
        holder.imgMidea.setImageResource(media.mediaImage)

    }

    interface OnMideaClickListener{
         fun mideaClick(position: Int)

    }

    inner class MideasViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val imgMidea: ImageView = itemView.findViewById(R.id.img_MediaMedias)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = adapterPosition
            if(RecyclerView.NO_POSITION != position){
                listener.mideaClick(position)
            }
        }
    }
}