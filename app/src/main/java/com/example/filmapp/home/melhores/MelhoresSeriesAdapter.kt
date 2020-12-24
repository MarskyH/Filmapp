package com.example.filmapp.home.melhores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.R
import com.squareup.picasso.Picasso

class MelhoresSeriesAdapter(val listener: onMelhoresSerieClickListener) :
    RecyclerView.Adapter<MelhoresSeriesAdapter.MelhoresSerieListsViewHolder>() {

    var mediaList = arrayListOf<ResultTv>()
    private var assistirMaisTardeIndicationBoolean = false
    private var evaluationIndicationBoolean = false
    private var shareIndicationBoolean = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MelhoresSerieListsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_medialist, parent, false)
        return MelhoresSerieListsViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MelhoresSerieListsViewHolder,
        position: Int
    ) {
        val currentItem: ResultTv = mediaList[position]

        holder.mediaName.text = currentItem.original_name
        holder.mediaFirstAirDate.text = currentItem.first_air_date
        holder.ratingStarsNumber.rating = currentItem.numberStars.toFloat()
        holder.mediaEvaluation.text = currentItem.vote_average.toString() + "/5"
        holder.mediaPosition.text = (position + 1).toString() + ". "

        var url = "https://image.tmdb.org/t/p/w500" + currentItem.poster_path
        Picasso.get().load(url).into(holder.mediaImage)

        holder.assistirMaisTardeIndication.setOnClickListener {
            if(assistirMaisTardeIndicationBoolean == false){
                holder.assistirMaisTardeIndication.setImageResource(R.drawable.ic_assistir_mais_tarde_roxo)
                assistirMaisTardeIndicationBoolean = true
            }else{
                holder.assistirMaisTardeIndication.setImageResource(R.drawable.ic_assistir_mais_tarde)
                assistirMaisTardeIndicationBoolean = false
            }
        }

        holder.followingStatusIndication.setOnClickListener {
            if(evaluationIndicationBoolean == false){
                holder.followingStatusIndication.setImageResource(R.drawable.ic_acompanhando_roxo)
                evaluationIndicationBoolean = true
            }else{
                holder.followingStatusIndication.setImageResource(R.drawable.ic_acompanhando)
                evaluationIndicationBoolean = false
            }
        }

        holder.shareIndication.setOnClickListener {
            if(shareIndicationBoolean == false){
                holder.shareIndication.setImageResource(R.drawable.ic_compartilhar_roxo)
                shareIndicationBoolean = true
            }else{
                holder.shareIndication.setImageResource(R.drawable.ic_compartilhar)
                shareIndicationBoolean = false
            }
        }

    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    interface onMelhoresSerieClickListener {
        fun melhoresItemClick(position: Int)
    }

    inner class MelhoresSerieListsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val mediaName: TextView = itemView.findViewById(R.id.tv_mediaName_medialist)
        val mediaImage: ImageView = itemView.findViewById(R.id.iv_mediaImage_medialist)
        val mediaFirstAirDate: TextView = itemView.findViewById(R.id.tv_mediaFirstAirDate_medialist)
        val ratingStarsNumber: RatingBar = itemView.findViewById(R.id.ratingBar_mediaItem)
        val mediaEvaluation: TextView = itemView.findViewById(R.id.tv_evaluation_medialist)
        val mediaPosition: TextView = itemView.findViewById(R.id.tv_mediaPosition_medialist)

        val assistirMaisTardeIndication: ImageView = itemView.findViewById(R.id.assistirMaisTardeIndication_medialist)
        val followingStatusIndication: ImageView = itemView.findViewById(R.id.followingStatusIndication_medialist)
        val shareIndication: ImageView = itemView.findViewById(R.id.shareIndication_medialist)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.melhoresItemClick(position)
            }
        }

    }

    fun addList(list: ArrayList<ResultTv>) {
        mediaList.addAll(list)
        notifyDataSetChanged()
    }
}
