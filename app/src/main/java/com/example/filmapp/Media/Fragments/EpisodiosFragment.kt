package com.example.filmapp.Series.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.R
import com.example.filmapp.Series.Adapter.EpisodiosAdapter
import kotlinx.android.synthetic.main.fragment_series_espisodios.view.*



class EpisodiosFragment(val id_tv: Int?, val season_number: Int?) : Fragment(), EpisodiosAdapter.OnEpisodioClickListener {
    var listaEpisodios = getAllEpisodios()
    var adapter = EpisodiosAdapter(listaEpisodios, this)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_series_espisodios, container, false)
        view.rv_episodios.adapter = adapter
        view.rv_episodios.layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)
        view.rv_episodios.setHasFixedSize(true)
        return  view

    }

    override fun episodioClick(position: Int) {
        val episodio = listaEpisodios.get(position)
        episodio.titulo = "DEU CERTO"
        adapter.notifyItemChanged(position)
    }
}
