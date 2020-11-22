package com.example.filmapp.Series.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.R
import com.example.filmapp.Series.Adapter.SeriesAdapter
import com.example.filmapp.Series.Classes.Serie
import kotlinx.android.synthetic.main.fragment_series_seasons.*
import kotlinx.android.synthetic.main.fragment_series_seasons.view.*


class SeasonsFragment : Fragment(), SeriesAdapter.OnSerieClickListener {
    var listaSeries = getAllTemporadas()
    var adapter = SeriesAdapter(listaSeries, this)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_series_seasons, container, false)
        view.rv_temporada.adapter = adapter
        view.rv_temporada.layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)
        view.rv_temporada.setHasFixedSize(true)
        return  view

    }
    
    fun getAllTemporadas(): ArrayList<Serie>{
        val serie1 = Serie(1, "The Boys", "Temporada 1", R.drawable.the_boys_image02)
        val serie2 = Serie(2, "The Boys", "Temporada 2", R.drawable.the_boys_image03)
        val serie3 = Serie(1, "The Boys", "Temporada 1", R.drawable.the_boys_image02)
        val serie4 = Serie(2, "The Boys", "Temporada 2", R.drawable.the_boys_image03)
        val serie5 = Serie(1, "The Boys", "Temporada 1", R.drawable.the_boys_image02)
        val serie6 = Serie(2, "The Boys", "Temporada 2", R.drawable.the_boys_image03)
        val serie7 = Serie(2, "The Boys", "Temporada 2", R.drawable.the_boys_image03)

        return arrayListOf(serie1, serie2)
    }

    override fun serieClick(position: Int) {
        val serie = listaSeries.get(position)
        serie.temporada = "DEU CERTO"
        adapter.notifyItemChanged(position)
    }
}