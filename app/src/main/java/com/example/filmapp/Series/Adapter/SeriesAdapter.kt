package com.example.filmapp.Series.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.R
import com.example.filmapp.Series.Classes.Serie


class SeriesAdapter(private var listSeries: ArrayList<Serie>, val listener: OnSerieClickListener): RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeriesViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_series_temporadas, parent, false)
        return SeriesViewHolder(itemView)
    }

    override fun getItemCount() = listSeries.size


    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
       var serie = listSeries.get(position)

        holder.tvTemporada.text = serie.temporada
        holder.imgTemporada.setImageResource(serie.imgTemporada)
    }

    interface OnSerieClickListener{
         fun serieClick(position: Int)

    }

    inner class SeriesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val tvTemporada: TextView = itemView.findViewById(R.id.tv_temporada)
        val imgTemporada: ImageView = itemView.findViewById(R.id.img_SerieTemporada)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = adapterPosition
            if(RecyclerView.NO_POSITION != position){
                listener.serieClick(position)
            }
        }
    }
}