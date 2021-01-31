package com.example.filmapp.home.ajuda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.All.Ajuda
import com.example.filmapp.R

class NovidadesAdapter(
    val listener: onNovidadeItemClickListener
) : RecyclerView.Adapter<NovidadesAdapter.NovidadeViewHolder>() {

    var novidadesList = arrayListOf<Ajuda>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NovidadeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ajudalists, parent, false)
        return NovidadeViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: NovidadeViewHolder,
        position: Int
    ) {
        val currentItem: Ajuda = novidadesList[position]

        holder.titleNovidade.setText("- " + currentItem.title)

    }

    override fun getItemCount(): Int {
        return novidadesList.size
    }

    interface onNovidadeItemClickListener {
        fun novidadeItemClick(position: Int)
    }

    inner class NovidadeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val titleNovidade: TextView = itemView.findViewById(R.id.tv_titleAjuda)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.novidadeItemClick(position)
            }
        }
    }

    fun addList(list: ArrayList<Ajuda>) {
        novidadesList = list
        notifyDataSetChanged()
    }

}