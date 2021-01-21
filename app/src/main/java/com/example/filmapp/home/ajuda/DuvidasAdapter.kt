package com.example.filmapp.home.ajuda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Ajuda
import com.example.filmapp.R

class DuvidasAdapter(
    val listener: onDuvidaItemClickListener
) : RecyclerView.Adapter<DuvidasAdapter.DuvidaViewHolder>() {

    var duvidasList = arrayListOf<Ajuda>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DuvidaViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ajudalists, parent, false)
        return DuvidaViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: DuvidaViewHolder,
        position: Int
    ) {
        val currentItem: Ajuda = duvidasList[position]

        holder.titleDuvida.setText(currentItem.titleAjuda)

    }

    override fun getItemCount(): Int {
        return duvidasList.size
    }

    interface onDuvidaItemClickListener {
        fun duvidaItemClick(position: Int)
    }

    inner class DuvidaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val titleDuvida: TextView = itemView.findViewById(R.id.tv_titleAjuda)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.duvidaItemClick(position)
            }
        }
    }

    fun addList(list: ArrayList<Ajuda>) {
        duvidasList.addAll(list)
        notifyDataSetChanged()
    }

}