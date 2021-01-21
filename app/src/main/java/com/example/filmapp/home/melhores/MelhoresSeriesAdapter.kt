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

        holder.mediaName.text = currentItem.formattedName
        holder.mediaFirstAirDate.text = currentItem.first_air_date
        holder.ratingStarsNumber.rating = currentItem.numberStars.toFloat()
        holder.mediaEvaluation.text = currentItem.vote_average.toString() + "/5"
        holder.mediaPosition.text = (position + 1).toString() + ". "

        var url = "https://image.tmdb.org/t/p/w500" + currentItem.poster_path
        Picasso.get().load(url).into(holder.mediaImage)

        //Aq verifica se a série já foi add a lista de Assistir Mais Tarde ou não
        if(currentItem.assistirMaisTardeIndication == true){
            holder.assistirMaisTardeIndication.setImageResource(R.drawable.ic_assistir_mais_tarde_roxo)
        }else{
            holder.assistirMaisTardeIndication.setImageResource(R.drawable.ic_assistir_mais_tarde)
        }


        holder.assistirMaisTardeIndication.setOnClickListener {
            if(currentItem.assistirMaisTardeIndication == false){
                holder.assistirMaisTardeIndication.setImageResource(R.drawable.ic_assistir_mais_tarde_roxo)
                listener.saveInAssistirMaisTardeList(position)
                currentItem.assistirMaisTardeIndication = true
            }else{
                holder.assistirMaisTardeIndication.setImageResource(R.drawable.ic_assistir_mais_tarde)
                listener.removeOfAssistirMaisTardeList(position)
                currentItem.assistirMaisTardeIndication = false
            }
        }

        //Aq verifica se a série está sendo acompanhada ou não
        if(currentItem.followingStatusIndication == true){
            if(currentItem.finished == true){
                holder.followingStatusIndication.setImageResource(R.drawable.ic_check_box_roxo)
            }else {
                holder.followingStatusIndication.setImageResource(R.drawable.ic_acompanhando_roxo)
            }
        }else{
            holder.followingStatusIndication.setImageResource(R.drawable.ic_acompanhando)
        }


        holder.followingStatusIndication.setOnClickListener {
            if(currentItem.followingStatusIndication == false){
                holder.followingStatusIndication.setImageResource(R.drawable.ic_acompanhando_roxo)
                listener.saveInAcompanhandoList(position)
                currentItem.followingStatusIndication = true
            }else{
                holder.followingStatusIndication.setImageResource(R.drawable.ic_acompanhando)
                listener.removeOfAcompanhandoList(position)
                currentItem.followingStatusIndication = false
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
        fun saveInAssistirMaisTardeList(position: Int)
        fun removeOfAssistirMaisTardeList(position: Int)
        fun saveInAcompanhandoList(position: Int)
        fun removeOfAcompanhandoList(position: Int)
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
