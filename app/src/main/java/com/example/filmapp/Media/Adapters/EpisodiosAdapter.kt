package com.example.filmapp.Series.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.TV.SeasonDetails
import com.example.filmapp.R
import com.example.filmapp.Series.Ui.SerieEpisodioSelectedActivity
import com.squareup.picasso.Picasso


class EpisodiosAdapter(private var listEpisodios: SeasonDetails, val listener: OnEpisodioClickListener): RecyclerView.Adapter<EpisodiosAdapter.EpisodiosViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodiosViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_series_episodio, parent, false)
        return EpisodiosViewHolder(itemView)
    }

    override fun getItemCount() = listEpisodios.episodes.size


    override fun onBindViewHolder(holder: EpisodiosViewHolder, position: Int) {
        val episodio = listEpisodios.episodes.get(position)
        val picasso = Picasso.get()
        val pathImg = episodio.still_path
        val baseURl = "https://image.tmdb.org/t/p/"
        val size = "original"
        val img = "${baseURl}${size}${pathImg}".replace("http://","https://")
        picasso.load(img).into(holder.imgEpisodio)
        holder.tvTitulo.text = episodio.name
        holder.imgEpisodio.setOnClickListener {
            val intent = Intent(holder.itemView.context, SerieEpisodioSelectedActivity::class.java)
            intent.putExtra("number_episode", episodio.episode_number)
            intent.putExtra("sinopse_episode", episodio.overview)
            intent.putExtra("number_season", episodio.season_number)
            intent.putExtra("imagem", episodio.still_path)
            holder.itemView.context.startActivity(intent)
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