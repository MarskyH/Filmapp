package com.example.filmapp.Series.Fragments

import android.content.Intent
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
import com.example.filmapp.Series.Ui.SerieEpisodioSelectedActivity
import com.example.filmapp.Services.service
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
        val Serie =  Serie as TvDetails?
        val intent = Intent(context, SerieEpisodioSelectedActivity::class.java)
        intent.putExtra("number_episode", episodio.episode_number)
        intent.putExtra("id_ep", episodio.id)
        intent.putExtra("sinopse_episode", episodio.overview)
        intent.putExtra("number_season", episodio.season_number)
        intent.putExtra("imagem", listaEpisodios.poster_path)
        intent.putExtra("logo", Serie?.networks?.get(0)?.logo_path)
        intent.putExtra("homepage", Serie?.homepage)
        intent.putExtra("id", Serie?.id.toString())
        intent.putExtra("title", Serie?.name)
        intent.putExtra("poster", Serie?.poster_path)
        startActivity(intent)
        adapter.notifyItemChanged(position)
    }
}
