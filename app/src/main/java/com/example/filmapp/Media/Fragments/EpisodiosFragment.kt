package com.example.filmapp.Series.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Entities.TV.SeasonDetails
import com.example.filmapp.Media.Models.TemporadaFragmentViewModel
import com.example.filmapp.R
import com.example.filmapp.Series.Adapter.EpisodiosAdapter
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.main.fragment_series_espisodios.view.*



class EpisodiosFragment(val id_tv: Int?, val season_number: Int?) : Fragment(), EpisodiosAdapter.OnEpisodioClickListener {
    lateinit var listaEpisodios: SeasonDetails
    lateinit var adapter : EpisodiosAdapter


    private val viewModel by viewModels<TemporadaFragmentViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(service) as T
            }
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.getSeasonDetails(id_tv.toString(), season_number.toString())
        viewModel.listSeasonDetails.observe(viewLifecycleOwner){
            listaEpisodios = it
        }
        adapter = EpisodiosAdapter(listaEpisodios, this)
        val view: View = inflater!!.inflate(R.layout.fragment_series_espisodios, container, false)
        view.rv_episodios.adapter = adapter
        view.rv_episodios.layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)
        view.rv_episodios.setHasFixedSize(true)
        return  view

    }

    override fun episodioClick(position: Int) {
        val episodio = listaEpisodios.episodes.get(position)
        adapter.notifyItemChanged(position)
    }
}
