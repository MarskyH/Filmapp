package com.example.filmapp.Media.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.squareup.picasso.Picasso

class HomeMediaSerieAdapter(
    private var listMediaSerie: ArrayList<ResultTv>,
    val listener: OnHomeMediaSerieClickListener,
    val Movie: Boolean?, var config: Config) : RecyclerView.Adapter<HomeMediaSerieAdapter.HomeMediasSeriesViewHolder>() {
    val picasso = Picasso.get()

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
        var baseURl = config.images.secure_base_url
        var size = "original"
        val pathImg = homeSerie.poster_path
        val img = "${baseURl}${size}${pathImg}".replace("http://","https://")
        picasso.load(img).into(holder.img)
        holder.titulo.text = homeSerie.name
        holder.img.setOnClickListener {
            val intent = Intent(holder.itemView.context, MediaSelectedActivity::class.java)
            if (homeSerie != null){
                intent.putExtra("poster", img)
                intent.putExtra("movie", Movie)
                intent.putExtra("mediaSerie", homeSerie)
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