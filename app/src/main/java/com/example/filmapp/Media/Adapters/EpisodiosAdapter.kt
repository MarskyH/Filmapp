package com.example.filmapp.Series.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.APIConfig.URL_IMAGE
import com.example.filmapp.Entities.TV.SeasonDetails
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.R
import com.example.filmapp.Series.Ui.SerieEpisodioSelectedActivity
import com.squareup.picasso.Picasso


class EpisodiosAdapter(private var listEpisodios: SeasonDetails,
                       val listener: OnEpisodioClickListener,
                       val Serie: TvDetails): RecyclerView.Adapter<EpisodiosAdapter.EpisodiosViewHolder>() {

    val picasso = Picasso.get()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodiosViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_series_episodio, parent, false)
        return EpisodiosViewHolder(itemView)
    }

    override fun getItemCount() = listEpisodios.episodes.size


    override fun onBindViewHolder(holder: EpisodiosViewHolder, position: Int) {
        val episodio = listEpisodios.episodes.get(position)
        if(episodio.still_path != "" && episodio.still_path != null){
            picasso.load(URL_IMAGE + episodio.still_path).into(holder.imgEpisodio)
        }else{
            picasso.load(R.drawable.sem_imagem).into(holder.imgEpisodio)
        }
        if(episodio.name != "" && episodio.name != null){
            holder.tvTitulo.text = episodio.name
        }else{
            holder.tvTitulo.text = "Nome do episódio indisponível"
        }
    }

    interface OnEpisodioClickListener{
         fun episodioClick(position: Int)

    }

    inner class EpisodiosViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val tvTitulo: TextView = itemView.findViewById(R.id.tv_titulo_ep)
        val imgEpisodio: ImageView = itemView.findViewById(R.id.img_SerieEpisodio)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = adapterPosition
            if(RecyclerView.NO_POSITION != position){
                listener.episodioClick(position)
            }
        }
    }
}