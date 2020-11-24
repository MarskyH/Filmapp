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
import com.example.filmapp.Series.Classes.Media
import com.example.filmapp.Series.Classes.Serie
import com.example.filmapp.Series.Ui.SerieSelectedActivity


class HomeSeriesAdapter(private var listMedias: ArrayList<com.example.filmapp.Classes.Media>, val listener: OnHomeSerieClickListener): RecyclerView.Adapter<HomeSeriesAdapter.HomeSeriesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeSeriesViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return HomeSeriesViewHolder(itemView)
    }

    override fun getItemCount() = listMedias.size


    override fun onBindViewHolder(holder: HomeSeriesViewHolder, position: Int) {
       var homeMedia = listMedias.get(position)
        holder.img.setImageResource(homeMedia.mediaImage)
        holder.titulo.text = homeMedia.mediaName
        holder.img.setOnClickListener {
            val intent = Intent(holder.itemView.context, SerieSelectedActivity::class.java)
            var bundle = Bundle()
            bundle.putInt("imagem", homeMedia.mediaImage)
            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)
        }


    }

    interface OnHomeSerieClickListener{
         fun homeSerieClick(position: Int)

    }

    inner class HomeSeriesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val img: ImageView = itemView.findViewById(R.id.mediaImage)
        val titulo: TextView = itemView.findViewById(R.id.mediaName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = adapterPosition
            if(RecyclerView.NO_POSITION != position){
                listener.homeSerieClick(position)
            }
        }
    }
}