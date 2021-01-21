package com.example.filmapp.Series.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.TV.SeasonDetails
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.R
import com.example.filmapp.Series.Ui.SerieEpisodioSelectedActivity
import com.squareup.picasso.Picasso


class EpisodiosAdapter(private var listEpisodios: SeasonDetails, val listener: OnEpisodioClickListener, val Serie: TvDetails): RecyclerView.Adapter<EpisodiosAdapter.EpisodiosViewHolder>() {
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
        var season = listEpisodios.poster_path
        val picasso = Picasso.get()
        val pathImg = episodio.still_path
        val logoPath = Serie.networks.get(0).logo_path
        Log.i("logo", logoPath)
        val baseURl = "https://image.tmdb.org/t/p/"
        val size = "original"
        val img = "${baseURl}${size}${pathImg}".replace("http://","https://")
        val poster = "${baseURl}${size}${Serie.poster_path}".replace("http://","https://")
        val imgLogo ="${baseURl}${size}${logoPath}".replace("http://","https://")
        Log.i("logo path", imgLogo)
        season = "${baseURl}${size}${season}".replace("http://","https://")
        picasso.load(img).into(holder.imgEpisodio)
        holder.tvTitulo.text = episodio.name
        holder.imgEpisodio.setOnClickListener {
            val intent = Intent(holder.itemView.context, SerieEpisodioSelectedActivity::class.java)
            intent.putExtra("number_episode", episodio.episode_number)
            intent.putExtra("sinopse_episode", episodio.overview)
            intent.putExtra("number_season", episodio.season_number)
            intent.putExtra("imagem", season)
            intent.putExtra("logo", imgLogo)
            intent.putExtra("homepage", Serie.homepage)
            intent.putExtra("id", Serie.id.toString())
            intent.putExtra("title", Serie.name)
            intent.putExtra("poster", poster)
            Log.i("Episodio", Serie.id.toString())
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