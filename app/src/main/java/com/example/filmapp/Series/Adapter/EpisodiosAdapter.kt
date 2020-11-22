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
import com.example.filmapp.Series.Ui.SerieEpisodioSelectedActivity


class EpisodiosAdapter(private var listEpisodios: ArrayList<Episodio>, val listener: OnEpisodioClickListener): RecyclerView.Adapter<EpisodiosAdapter.EpisodiosViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodiosViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_series_episodio, parent, false)
        return EpisodiosViewHolder(itemView)
    }

    override fun getItemCount() = listEpisodios.size


    override fun onBindViewHolder(holder: EpisodiosViewHolder, position: Int) {
       var episodio = listEpisodios.get(position)

        holder.tvTitulo.text = episodio.titulo
        holder.imgEpisodio.setImageResource(episodio.img)
        holder.imgEpisodio.setOnClickListener {
            val intent = Intent(holder.itemView.context, SerieEpisodioSelectedActivity::class.java)
            var bundle = Bundle()
            bundle.putInt("Episodio", episodio.id)
            bundle.putInt("Imagem Episodio", episodio.img)
            intent.putExtras(bundle)
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