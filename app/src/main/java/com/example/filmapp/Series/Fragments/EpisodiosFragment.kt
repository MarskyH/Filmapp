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
import com.example.filmapp.Series.Classes.Episodio
import kotlinx.android.synthetic.main.fragment_series_espisodios.view.*



class EpisodiosFragment : Fragment(), EpisodiosAdapter.OnEpisodioClickListener {
    var listaEpisodios = getAllEpisodios()
    var adapter = EpisodiosAdapter(listaEpisodios, this)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_series_espisodios, container, false)
        view.rv_episodios.adapter = adapter
        view.rv_episodios.layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)
        view.rv_episodios.setHasFixedSize(true)
        return  view

    }

    fun getAllEpisodios(): ArrayList<Episodio> {
        val episodio1 = Episodio(1, "2x1 Açogueiro, padreiro, fabricante de velas", R.drawable.the_boys_image03)
        val episodio2 = Episodio(2, "2x2 Nada igual no mundo", R.drawable.the_boys_image03)
        val episodio3 = Episodio(3, "2x3 Sobre a colina com as espadas de mil homens", R.drawable.the_boys_image03)
        val episodio4 = Episodio(4, "2x4 Preparação e planejamento adequado", R.drawable.the_boys_image03)
        val episodio5 = Episodio(5, "2x5 The Big Ride", R.drawable.the_boys_image03)
        val episodio6 = Episodio(6, "2x6 The Bloody Doors Off", R.drawable.the_boys_image03)
        val episodio7 = Episodio(7, "2x7 O começo da ruína", R.drawable.the_boys_image03)
        val episodio8 = Episodio(8, "2x7 O fim dos heróis", R.drawable.the_boys_image03)

        return arrayListOf(episodio1, episodio2, episodio3, episodio4, episodio5, episodio6, episodio7, episodio8)
    }

    override fun episodioClick(position: Int) {
        val episodio = listaEpisodios.get(position)
        episodio.titulo = "DEU CERTO"
        adapter.notifyItemChanged(position)
    }
}
