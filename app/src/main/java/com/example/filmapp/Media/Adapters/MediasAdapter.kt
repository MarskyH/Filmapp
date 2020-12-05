package com.example.filmapp.Media.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.R



class MediasAdapter(private var listMedias: ArrayList<com.example.filmapp.Classes.Media>, val listener: OnMediaClickListener): RecyclerView.Adapter<MediasAdapter.MediasViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MediasViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_media_especifica, parent, false)
        return MediasViewHolder(itemView)
    }

    override fun getItemCount() = listMedias.size


    override fun onBindViewHolder(holder: MediasViewHolder, position: Int) {
       var media = listMedias.get(position)
        holder.tvMediaEspecifica.text = media.mediaName
        holder.imgMediaEspecica.setImageResource(media.mediaImage)
//        holder.imgMediaEspecica.setOnClickListener {
//            val intent = Intent(holder.itemView.context, SerieTemporadaActivity::class.java)
//            var bundle = Bundle()
//            bundle.putString("Temporada", serie.temporada)
//            bundle.putInt("Imagem Temporada", serie.imgTemporada)
//            intent.putExtras(bundle)
//            holder.itemView.context.startActivity(intent)
//        }
    }

    interface OnMediaClickListener{
         fun mediaClick(position: Int)

    }

    inner class MediasViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val tvMediaEspecifica: TextView = itemView.findViewById(R.id.tv_mediaEspecifica)
        val imgMediaEspecica: ImageView = itemView.findViewById(R.id.img_MediaEspecifica)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?){
            val position = adapterPosition
            if(RecyclerView.NO_POSITION != position){
                listener.mediaClick(position)
            }
        }
    }
}