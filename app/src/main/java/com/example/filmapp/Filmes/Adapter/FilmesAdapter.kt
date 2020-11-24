package com.example.filmapp.Filmes.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Filmes.Classes.Filme
import com.example.filmapp.R
import com.example.filmapp.Series.Classes.Serie


class FilmesAdapter(private var listFilmes: ArrayList<Filme>, val listener: OnFilmeClickListener): RecyclerView.Adapter<FilmesAdapter.FilmesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmesViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_filmes_series, parent, false)
        return FilmesViewHolder(itemView)
    }

    override fun getItemCount() = listFilmes.size


    override fun onBindViewHolder(holder: FilmesViewHolder, position: Int) {
        var filme = listFilmes.get(position)

        holder.tvFilme.text = filme.titulo
        holder.imgFilme.setImageResource(filme.imgFilme)
    }

    interface OnFilmeClickListener{
        fun filmeClick(position: Int)

    }

    inner class FilmesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val tvFilme: TextView = itemView.findViewById(R.id.tv_filme)
        val imgFilme: ImageView = itemView.findViewById(R.id.img_FilmeSemelhante)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = adapterPosition
            if(RecyclerView.NO_POSITION != position){
                listener.filmeClick(position)
            }
        }
    }
}