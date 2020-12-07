package com.example.filmapp.Media.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.squareup.picasso.Picasso

class HomeMediaSerieAdapter(
    private var listMediaSerie: ArrayList<ResultTv>,
    val listener: OnHomeMediaSerieClickListener,
    val Movie: Boolean) : RecyclerView.Adapter<HomeMediaSerieAdapter.HomeMediasSeriesViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeMediasSeriesViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)

        return HomeMediasSeriesViewHolder(itemView)
    }


    override fun getItemCount() = listMediaSerie.size


    override fun onBindViewHolder(holder: HomeMediasSeriesViewHolder, position: Int) {
        var homeSerie = listMediaSerie[position]
        holder.titulo.text = homeSerie.name
        val picasso = Picasso.get()
        val pathImg = homeSerie.poster_path
        val img = "${pathImg}".replace("http://","https://")
        picasso.load(img).into(holder.img)
        holder.img.setOnClickListener {
            val intent = Intent(holder.itemView.context, MediaSelectedActivity::class.java)
            if (homeSerie != null){
                intent.putExtra("media", homeSerie)
                holder.itemView.context.startActivity(intent)
            }
        }

    }

    interface OnHomeMediaSerieClickListener {
        fun homeMediaSerieClick(position: Int)

    }

    inner class HomeMediasSeriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val img: ImageView = itemView.findViewById(R.id.mediaImage)
        val titulo: TextView = itemView.findViewById(R.id.mediaName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.homeMediaSerieClick(position)
            }
        }
    }

}