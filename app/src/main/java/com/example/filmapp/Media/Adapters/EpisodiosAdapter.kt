package com.example.filmapp.Series.Adapter

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
import com.squareup.picasso.Picasso

class EpisodiosAdapter(private var listEpisodios: SeasonDetails,
                       val listener: OnEpisodioClickListener,
                       val Serie: TvDetails): RecyclerView.Adapter<EpisodiosAdapter.EpisodiosViewHolder>() {

    val picasso = Picasso.get()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodiosViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_episodioslist, parent, false)
        return EpisodiosViewHolder(itemView)
    }

    override fun getItemCount() = listEpisodios.episodes.size

    override fun onBindViewHolder(holder: EpisodiosViewHolder, position: Int) {
        val episodio = listEpisodios.episodes.get(position)

        //Definindo Imagem do episódio
        if(episodio.still_path != "" && episodio.still_path != null){
            picasso.load(URL_IMAGE + episodio.still_path).into(holder.imgEpisodio)
        }else{
            holder.imgEpisodio.visibility = View.GONE
        }

        //Definindo Nome do Episódio
        if(episodio.name != "" && episodio.name != null){
            holder.tvTitulo.text = episodio.name
        }else{
            holder.tvTitulo.text = episodio.air_date //Arrumar
        }

        //Definindo a palavra "Episódio" (varia com a liguagem do app)
        holder.wordEpisodio.setText(R.string.wordEpisodio)

        //Definindo o número do episódio
        holder.episodeNumber.text = " " + (position + 1).toString()

        //Definindo a Data de Lançamento do episódio
        holder.episodeAirDate.text = episodio.air_date

        //Aq verifica se o episódio já foi assistido ou não
        if(episodio.watched == true){
            holder.watchedIndication.setImageResource(R.drawable.ic_visto_grande_roxo)
        }else{
            holder.watchedIndication.setImageResource(R.drawable.ic_visto_grande)
        }


        holder.watchedIndication.setOnClickListener {
            if(episodio.watched == false){
                holder.watchedIndication.setImageResource(R.drawable.ic_visto_grande_roxo)
//                listener.saveInHistorico(position)
                episodio.watched = true
            }else{
                holder.watchedIndication.setImageResource(R.drawable.ic_visto_grande)
//                listener.removeOfHistorico(position)
                episodio.watched = false
            }
        }

    }

    interface OnEpisodioClickListener{
         fun episodioClick(position: Int)

    }

    inner class EpisodiosViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val tvTitulo: TextView = itemView.findViewById(R.id.episodeName)
        val imgEpisodio: ImageView = itemView.findViewById(R.id.episodeImage)
        val episodeNumber: TextView = itemView.findViewById(R.id.episodeNumber)
        val episodeAirDate: TextView = itemView.findViewById(R.id.episodeAirDate)
        val wordEpisodio: TextView = itemView.findViewById(R.id.wordEpisodio)
        val watchedIndication: ImageView = itemView.findViewById(R.id.watchedIndication)

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