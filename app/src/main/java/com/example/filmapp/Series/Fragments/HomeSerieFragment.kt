package com.example.filmapp.Series.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.R
import com.example.filmapp.Series.Adapter.HomeSeriesAdapter
import com.example.filmapp.Series.Adapter.SeriesAdapter
import com.example.filmapp.Series.Classes.Serie
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


class HomeSerieFragment : Fragment(), HomeSeriesAdapter.OnHomeSerieClickListener {
    val scope = CoroutineScope(Dispatchers.Main)
    var listaSeries = getAllHomeSeries()
    var adapter = HomeSeriesAdapter(listaSeries, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_home_series, container, false)
        view.rv_series_fav.adapter = adapter
        view.rv_series_pop.adapter = adapter
        view.rv_series_fav.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.rv_series_pop.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.rv_series_pop.setHasFixedSize(true)
        view.rv_series_fav.setHasFixedSize(true)
        return view
    }

    fun getAllHomeSeries(): ArrayList<Serie>{
        val serie1 = Serie(1, "The Boys", "Temporada 1", R.drawable.the_boys_image02)
        val serie2 = Serie(2, "The Boys", "Temporada 2", R.drawable.the_walking_dead_image01)
        val serie3 = Serie(1, "The Boys", "Temporada 1", R.drawable.flash_image01)
        val serie4 = Serie(2, "The Boys", "Temporada 2", R.drawable.grey_image01)
        val serie5 = Serie(1, "The Boys", "Temporada 1", R.drawable.the_walking_dead_image01)
        val serie6 = Serie(2, "The Boys", "Temporada 2", R.drawable.the_boys_image03)
        val serie7 = Serie(2, "The Boys", "Temporada 2", R.drawable.lucifer_image01)

        return arrayListOf(serie1, serie2, serie3, serie4, serie5, serie6, serie7)
    }


    override fun homeSerieClick(position: Int) {
        val serie = listaSeries.get(position)
        adapter.notifyItemChanged(position)
    }


}


