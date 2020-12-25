package com.example.filmapp.home.descubra

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.R
import com.squareup.picasso.Picasso

class DescubraSeriesAdapter(val listener: onDescubraSerieClickListener) :
    RecyclerView.Adapter<DescubraSeriesAdapter.DescubraSerieListsViewHolder>() {

    var mediaList = arrayListOf<ResultTv>()
//    private var assistirMaisTardeIndicationBoolean = false
//    private var evaluationIndicationBoolean = false
//    private var shareIndicationBoolean = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DescubraSerieListsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return DescubraSerieListsViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: DescubraSerieListsViewHolder,
        position: Int
    ) {
        val currentItem: ResultTv = mediaList[position]

        holder.mediaName.text = currentItem.original_name

        var url = "https://image.tmdb.org/t/p/w500" + currentItem.poster_path
        Picasso.get().load(url).into(holder.mediaImage)

//        holder.assistirMaisTardeIndication.setOnClickListener {
//            if(assistirMaisTardeIndicationBoolean == false){
//                holder.assistirMaisTardeIndication.setImageResource(R.drawable.ic_assistir_mais_tarde_roxo)
//                assistirMaisTardeIndicationBoolean = true
//            }else{
//                holder.assistirMaisTardeIndication.setImageResource(R.drawable.ic_assistir_mais_tarde)
//                assistirMaisTardeIndicationBoolean = false
//            }
//        }
//
//        holder.evaluationIndication.setOnClickListener {
//            if(evaluationIndicationBoolean == false){
//                holder.evaluationIndication.setImageResource(R.drawable.ic_estrela_vazia_roxo)
//                evaluationIndicationBoolean = true
//            }else{
//                holder.evaluationIndication.setImageResource(R.drawable.ic_estrela_vazia)
//                evaluationIndicationBoolean = false
//            }
//        }
//
//        holder.shareIndication.setOnClickListener {
//            if(shareIndicationBoolean == false){
//                holder.shareIndication.setImageResource(R.drawable.ic_compartilhar_roxo)
//                shareIndicationBoolean = true
//            }else{
//                holder.shareIndication.setImageResource(R.drawable.ic_compartilhar)
//                shareIndicationBoolean = false
//            }
//        }

    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    interface onDescubraSerieClickListener {
        fun descubraItemClick(position: Int)
    }

    inner class DescubraSerieListsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val mediaName: TextView = itemView.findViewById(R.id.mediaName)
        val mediaImage: ImageView = itemView.findViewById(R.id.mediaImage)
//        val mediaDetail1: TextView = itemView.findViewById(R.id.tv_mediaDetail1_medialist)
//        val mediaDetail2: TextView = itemView.findViewById(R.id.tv_mediaDetail2_medialist)

//        val assistirMaisTardeIndication: ImageView = itemView.findViewById(R.id.assistirMaisTardeIndication_medialist)
//        val evaluationIndication: ImageView = itemView.findViewById(R.id.evaluationIndication_medialist)
//        val shareIndication: ImageView = itemView.findViewById(R.id.shareIndication_medialist)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.descubraItemClick(position)
            }
        }

    }

    fun addList(list: ArrayList<ResultTv>) {
        mediaList.addAll(list)
        notifyDataSetChanged()
    }
}
