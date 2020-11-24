package com.example.filmapp.Series.Adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.R
import com.example.filmapp.Series.Classes.Serie
import com.example.filmapp.Series.Ui.SerieSelectedActivity


class HomeSeriesAdapter(private var listSeries: ArrayList<Serie>, val listener: OnHomeSerieClickListener): RecyclerView.Adapter<HomeSeriesAdapter.HomeSeriesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeSeriesViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_home, parent, false)
        return HomeSeriesViewHolder(itemView)
    }

    override fun getItemCount() = listSeries.size


    override fun onBindViewHolder(holder: HomeSeriesViewHolder, position: Int) {
       var homeSerie = listSeries.get(position)
        holder.img.setImageResource(homeSerie.imgTemporada)
        holder.img.setOnClickListener {
            val intent = Intent(holder.itemView.context, SerieSelectedActivity::class.java)
            var bundle = Bundle()
            bundle.putInt("imagem", homeSerie.imgTemporada)
            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)
        }


    }

    interface OnHomeSerieClickListener{
         fun homeSerieClick(position: Int)

    }

    inner class HomeSeriesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val img: ImageView = itemView.findViewById(R.id.img_SerieHome)

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