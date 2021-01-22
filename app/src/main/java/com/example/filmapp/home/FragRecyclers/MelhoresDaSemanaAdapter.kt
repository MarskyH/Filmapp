package com.example.filmapp.home.FragRecyclers

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.All.ResultAll
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.squareup.picasso.Picasso
import java.io.Serializable

class MelhoresDaSemanaAdapter(val listener: onMelhoresDaSemanaItemClickListener,
                              var mediaList: ResultAll):
    RecyclerView.Adapter<MelhoresDaSemanaAdapter.MelhoresDaSemanaViewHolder>() {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MelhoresDaSemanaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return MelhoresDaSemanaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MelhoresDaSemanaViewHolder, position: Int) {
//        val currentItem = mediaList.results.get(position)
//        val itemAtual = currentItem
//        val Movie = true
//        val url = "https://image.tmdb.org/t/p/w500" + currentItem.poster_path
//        Picasso.get().load(url).into(holder.mediaImage)
//        holder.mediaName.text = currentItem.title
//        holder.mediaImage.setOnClickListener {
//            val intent = Intent(holder.itemView.context, MediaSelectedActivity::class.java)
//            intent.putExtra("poster", url)
//            intent.putExtra("movie", Movie)
//            intent.putExtra("mediaSemana-", itemAtual)
//            holder.itemView.context.startActivity(intent)
//        }
    }

//    override fun getItemCount() = mediaList.results.size

    interface onMelhoresDaSemanaItemClickListener {
        fun melhoresDaSemanaItemClick(position: Int)
    }

    inner class MelhoresDaSemanaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val mediaName: TextView = itemView.findViewById(R.id.mediaName)
        val mediaImage: ImageView = itemView.findViewById(R.id.mediaImage)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.melhoresDaSemanaItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}