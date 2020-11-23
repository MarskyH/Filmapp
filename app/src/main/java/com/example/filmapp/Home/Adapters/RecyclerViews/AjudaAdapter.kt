package com.example.filmapp.Home.Adapters.RecyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Ajuda
import com.example.filmapp.R

class AjudaAdapter(private val ajudaList: ArrayList<Ajuda>, val listener: onAjudaItemClickListener): RecyclerView.Adapter<AjudaAdapter.AjudaViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AjudaAdapter.AjudaViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.item_ajudalists, parent, false)
        return AjudaViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: AjudaAdapter.AjudaViewHolder,
        position: Int
    ) {
        val currentItem: Ajuda = ajudaList[position]

        holder.titleAjuda.setText(currentItem.titleAjuda)

    }

    override fun getItemCount(): Int {
        return ajudaList.size
    }

    interface onAjudaItemClickListener{
        fun ajudaItemClick(position: Int)
    }

    inner class AjudaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val titleAjuda: TextView = itemView.findViewById(R.id.tv_titleAjuda)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.ajudaItemClick(position)
            }
        }
    }
}