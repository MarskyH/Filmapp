package com.example.filmapp.Series.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.R
import com.example.filmapp.Series.Classes.Media


class MideaAdapter(private var listMedia: ArrayList<Media>, val listener: OnMideaClickListener): RecyclerView.Adapter<MideaAdapter.MideasViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MideasViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_media_medias, parent, false)
        return MideasViewHolder(itemView)
    }

    override fun getItemCount() = listMedia.size


    override fun onBindViewHolder(holder: MideasViewHolder, position: Int) {
       var midea = listMedia.get(position)
        holder.imgMidea.setImageResource(midea.img)

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