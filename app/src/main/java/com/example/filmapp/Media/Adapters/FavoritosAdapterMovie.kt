package com.example.filmapp.Media.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.APIConfig.URL_IMAGE
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.Media.dataBase.FavoritosEntity
import com.example.filmapp.R
import com.squareup.picasso.Picasso

class FavoritosAdapterMovie(val listener: FavoritosItemClickListener) :
    RecyclerView.Adapter<FavoritosAdapterMovie.FavoritosViewHolder>() {

    var mediaList = listOf<FavoritosEntity>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritosViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return FavoritosViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        val currentItem: FavoritosEntity = mediaList[position]
        holder.mediaName.text = currentItem.title
        Picasso.get().load(URL_IMAGE + currentItem.poster_path).into(holder.mediaImage)

        holder.mediaImage.setOnClickListener {
            val intent = Intent(holder.itemView.context, MediaSelectedActivity::class.java)
                intent.putExtra("poster", currentItem.poster_path)
                intent.putExtra("movie", true)
                intent.putExtra("id", currentItem.id)
                holder.itemView.context.startActivity(intent)
            }
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    interface FavoritosItemClickListener {
        fun favoritosItemClick(position: Int)
    }

    inner class FavoritosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val mediaName: TextView = itemView.findViewById(R.id.mediaName)
        val mediaImage: ImageView = itemView.findViewById(R.id.mediaImage)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.favoritosItemClick(position)
            }
        }
    }

    fun addList(list: List<FavoritosEntity>) {
        mediaList = list
        notifyDataSetChanged()
    }

}