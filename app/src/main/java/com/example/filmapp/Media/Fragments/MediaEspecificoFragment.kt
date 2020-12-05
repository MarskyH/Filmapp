package com.example.filmapp.Media.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Media.Adapters.MediasAdapter
import com.example.filmapp.R
import com.example.filmapp.Series.Adapter.SeriesAdapter
import com.example.filmapp.Series.Classes.Media
import com.example.filmapp.Series.Classes.Serie
import kotlinx.android.synthetic.main.fragment_series_seasons.*
import kotlinx.android.synthetic.main.fragment_series_seasons.view.*


class MediaEspecificoFragment(val Movie: Boolean): Fragment(), MediasAdapter.OnMediaClickListener {
    private lateinit var listaMedias: ArrayList<com.example.filmapp.Classes.Media>
    lateinit var adapter : MediasAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (Movie == true){
             listaMedias = getAllMediasSemelhantes()
        }else{
            listaMedias = getAllTemporadas()
        }
        var adapter = MediasAdapter(listaMedias, this)
        val view: View = inflater!!.inflate(R.layout.fragment_series_seasons, container, false)
        view.rv_temporada.adapter = adapter
        view.rv_temporada.layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)
        view.rv_temporada.setHasFixedSize(true)
        return  view

    }
    
    fun getAllMediasSemelhantes(): ArrayList<com.example.filmapp.Classes.Media>{
        return arrayListOf(
            com.example.filmapp.Classes.Media(
                1,
                R.drawable.fim,
                "2012",
                "Série",
                "2x08 - O Que Eu Sei",
                "21/08/12",
                "Netflix",
                "4 Temporadas",
                "Sinopse de 2012"
            ),
            com.example.filmapp.Classes.Media(
                1,
                R.drawable.star_born,
                "Nasce um Estrela",
                "Filme",
                "",
                "08/08/12",
                "Amazon",
                "8 Temporadas",
                "Sinopse de Star"
            ),
            com.example.filmapp.Classes.Media(
                1,
                R.drawable.star_wars,
                "Star Wars: A Ascensão Skywalker",
                "Série",
                "2x08 - O Que Eu Sei",
                "21/09/12",
                "Netflix",
                "3 Temporadas",
                "Sinopse de Star wars"
            ),
            com.example.filmapp.Classes.Media(
                1,
                R.drawable.regresso,
                "O Regresso",
                "Filme",
                "",
                "21/08/18",
                "Amazon",
                "7 Temporadas",
                "Sinopse de O Regresso"
            ),
            com.example.filmapp.Classes.Media(
                1,
                R.drawable.animais,
                "Animais Fantásticos e os Crimes de Grindelwald",
                "Série",
                "2x08 - O Que Eu Sei",
                "75/08/12",
                "Netflix",
                "1 Temporadas",
                "Sinopse de Animais Fantásticos "
            ),
            com.example.filmapp.Classes.Media(
                1,
                R.drawable.ultimato,
                "Vingadores: Ultimato",
                "Série",
                "2x08 - O Que Eu Sei",
                "75/08/12",
                "Netflix",
                "1 Temporadas",
                "Sinopse de Vingadores Ultimato"
            )
        )
    }

    fun getAllTemporadas(): ArrayList<com.example.filmapp.Classes.Media>{
        val media1 = com.example.filmapp.Classes.Media(1,R.drawable.the_boys_image02,"The Boys", "Serie", "", "", "", "", "" )
        val media2 = com.example.filmapp.Classes.Media(2,R.drawable.the_boys_image03,"The Boys", "Serie", "", "", "", "", "" )


        return arrayListOf(media1, media2)
    }

    override fun mediaClick(position: Int) {
        val serie = listaMedias.get(position)
        adapter.notifyItemChanged(position)
    }
}