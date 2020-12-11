package com.example.filmapp.Series.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Entities.TV.Season
import com.example.filmapp.Entities.TV.SeasonDetails
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.Media.Models.TemporadaFragmentViewModel
import com.example.filmapp.R
import com.example.filmapp.Series.Adapter.EpisodiosAdapter
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_series_episodio.view.*
import kotlinx.android.synthetic.main.fragment_series_espisodios.view.*
import java.io.Serializable


class EpisodiosFragment() : Fragment(), EpisodiosAdapter.OnEpisodioClickListener {
    var listaEpisodios = SeasonDetails()
    lateinit var adapter : EpisodiosAdapter
    var Serie: Serializable? = null
    var Season: Serializable? = null

    private val viewModelTemporadaFragment by viewModels<TemporadaFragmentViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return TemporadaFragmentViewModel(service) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            Season = arguments?.getSerializable(season)
            Serie = arguments?.getSerializable(serie)
        }
    }

    companion object{
        private val season = "season"
        private val serie = "serie"
        private val path_logo = "path_logo"

        fun newInstance(Serie: Serializable?, Season: Serializable?): EpisodiosFragment {
            val fragment = EpisodiosFragment()
            val args = Bundle()
            args.putSerializable(season, Season)
            args.putSerializable(serie, Serie)
            fragment.arguments = args
            return fragment
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val TvSerie = Serie as TvDetails
        val TvSeason = Season as Season
        Log.i("number season", TvSeason.toString())
        Log.i("Serie ", TvSerie.id.toString())
        Log.i("API ", (viewModelTemporadaFragment.getSeasonDetails(TvSerie.id, TvSeason.season_number)).toString())
        val view: View = inflater!!.inflate(R.layout.fragment_series_espisodios, container, false)
        viewModelTemporadaFragment.listSeasonDetails.observe(viewLifecycleOwner){
            listaEpisodios = it
            Log.i("result", it.toString())
            adapter = EpisodiosAdapter(listaEpisodios, this, TvSerie)
            view.rv_episodios.adapter = adapter
            view.rv_episodios.layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)
            view.rv_episodios.setHasFixedSize(true)
        }
        viewModelTemporadaFragment.getSeasonDetails(TvSerie.id, TvSeason.season_number)
        return  view
    }

    override fun episodioClick(position: Int) {
        val episodio = listaEpisodios.episodes.get(position)
        adapter.notifyItemChanged(position)
    }
}
