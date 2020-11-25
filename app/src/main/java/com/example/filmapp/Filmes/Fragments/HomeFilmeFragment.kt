package com.example.filmapp.Filmes.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Classes.Media
import com.example.filmapp.Filmes.Adapter.HomeFilmesAdapter
import com.example.filmapp.R
import com.example.filmapp.Series.Adapter.HomeSeriesAdapter
import com.example.filmapp.Series.Adapter.SeriesAdapter
import com.example.filmapp.Series.Classes.Serie
import kotlinx.android.synthetic.main.fragment_home_filmes.view.*
import kotlinx.android.synthetic.main.fragment_home_series.view.*
import kotlinx.android.synthetic.main.fragment_series_episodio.*
import kotlinx.android.synthetic.main.fragment_series_episodio.view.*
import kotlinx.android.synthetic.main.fragment_series_geral.imgCompart
import kotlinx.android.synthetic.main.fragment_series_geral.imgFav
import kotlinx.android.synthetic.main.fragment_series_geral.imgTarde
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgCompart
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgFav
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgTarde
import kotlinx.android.synthetic.main.fragment_series_seasons.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFilmeFragment : Fragment(), HomeFilmesAdapter.OnHomeFilmeClickListener {
    val scope = CoroutineScope(Dispatchers.Main)
    var listaMedias = getMediaList()
    var adapter = HomeFilmesAdapter(listaMedias, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_home_filmes, container, false)
        view.rv_filmes_fav.adapter = adapter
        view.rv_filmes_pop.adapter = adapter
        view.rv_filmes_fav.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.rv_filmes_pop.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.rv_filmes_pop.setHasFixedSize(true)
        view.rv_filmes_fav.setHasFixedSize(true)
        return view
    }

    fun getMediaList(): ArrayList<Media>{
        return arrayListOf<Media>(
            Media(1,R.drawable.fim,"2012", "Série", "2x08 - O Que Eu Sei", "21/08/12", "Netflix", "4 Temporadas", "37 Episodeos"),
            Media(1,R.drawable.star_born,"Nasce um Estrela", "Filme", "", "08/08/12", "Amazon", "8 Temporadas", "2 Episodeos"),
            Media(1,R.drawable.star_wars,"Star Wars: A Ascensão Skywalker", "Série", "2x08 - O Que Eu Sei", "21/09/12", "Netflix", "3 Temporadas", "7 Episodeos"),
            Media(1,R.drawable.regresso,"O Regresso", "Filme", "", "21/08/18", "Amazon", "7 Temporadas", "87 Episodeos"),
            Media(1,R.drawable.animais,"Animais Fantásticos e os Crimes de Grindelwald", "Série", "2x08 - O Que Eu Sei", "75/08/12", "Netflix", "1 Temporadas", "10 Episodeos"),
            Media(1,R.drawable.ultimato,"Vingadores: Ultimato", "Série", "2x08 - O Que Eu Sei", "75/08/12", "Netflix", "1 Temporadas", "10 Episodeos")
        )
    }


    override fun homeFilmeClick(position: Int) {
        val serie = listaMedias.get(position)
        adapter.notifyItemChanged(position)
    }


}


