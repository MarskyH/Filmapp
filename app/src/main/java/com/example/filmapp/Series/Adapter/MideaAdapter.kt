package com.example.filmapp.Series.Adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.R
import com.example.filmapp.Series.Classes.Episodio
import com.example.filmapp.Series.Classes.Midea
import com.example.filmapp.Series.Ui.SerieEpisodioSelectedActivity


class MideaAdapter(private var listMideas: ArrayList<Midea>, val listener: OnMideaClickListener): RecyclerView.Adapter<MideaAdapter.MideasViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MideasViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_series_midea, parent, false)
        return MideasViewHolder(itemView)
    }

    override fun getItemCount() = listMideas.size


    override fun onBindViewHolder(holder: MideasViewHolder, position: Int) {
       var midea = listMideas.get(position)
        holder.imgMidea.setImageResource(midea.img)

    }

    interface OnMideaClickListener{
         fun mideaClick(position: Int)

    }

    inner class MideasViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val imgMidea: ImageView = itemView.findViewById(R.id.img_SerieMidea)

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