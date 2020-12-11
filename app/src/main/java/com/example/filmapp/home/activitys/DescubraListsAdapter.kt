package com.example.filmapp.home.activitys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.All.ResultSearchAll
import com.example.filmapp.R
import com.squareup.picasso.Picasso

class DescubraListsAdapter(val listener: onDescubraItemClickListener) :
    RecyclerView.Adapter<DescubraListsAdapter.DescubraListsViewHolder>() {

    var mediaList = arrayListOf<ResultSearchAll>()
    private var assistirMaisTardeIndicationBoolean = false
    private var evaluationIndicationBoolean = false
    private var shareIndicationBoolean = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DescubraListsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_medialist, parent, false)
        return DescubraListsViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: DescubraListsViewHolder,
        position: Int
    ) {
        val currentItem: ResultSearchAll = mediaList[position]

        holder.mediaName.text = currentItem.original_name
        holder.mediaDetail1.text = currentItem.first_air_date
        holder.mediaDetail2.text = currentItem.original_language

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

        holder.evaluationIndication.setOnClickListener {
            if(evaluationIndicationBoolean == false){
                holder.evaluationIndication.setImageResource(R.drawable.ic_estrela_vazia_roxo)
                evaluationIndicationBoolean = true
            }else{
                holder.evaluationIndication.setImageResource(R.drawable.ic_estrela_vazia)
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

    interface onDescubraItemClickListener {
        fun descubraItemClick(position: Int)
    }

    inner class DescubraListsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val mediaName: TextView = itemView.findViewById(R.id.tv_mediaName_medialist)
        val mediaImage: ImageView = itemView.findViewById(R.id.iv_mediaImage_medialist)
        val mediaDetail1: TextView = itemView.findViewById(R.id.tv_mediaDetail1_medialist)
        val mediaDetail2: TextView = itemView.findViewById(R.id.tv_mediaDetail2_medialist)

        val assistirMaisTardeIndication: ImageView = itemView.findViewById(R.id.assistirMaisTardeIndication_medialist)
        val evaluationIndication: ImageView = itemView.findViewById(R.id.evaluationIndication_medialist)
        val shareIndication: ImageView = itemView.findViewById(R.id.shareIndication_medialist)

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

    fun addList(list: ArrayList<ResultSearchAll>) {
        mediaList.addAll(list)
        notifyDataSetChanged()
    }
}
