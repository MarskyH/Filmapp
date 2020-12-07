package com.example.filmapp.Media.Fragments

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
import com.example.filmapp.Classes.Media
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.Movie.SimilarMovies
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.Media.Adapters.MediaEspecificoMovieAdapter
import com.example.filmapp.Media.Adapters.MediaEspecificoSerieAdapter
import com.example.filmapp.Media.Models.EspecificoFragmentViewModel
import com.example.filmapp.R
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.main.fragment_series_seasons.view.*


class MediaEspecificoFragment(val Movie: Boolean, var MediaSelect: Any?) :
    Fragment(),
    MediaEspecificoSerieAdapter.OnMediaSerieClickListener,
    MediaEspecificoMovieAdapter.OnMediaMovieClickListener {
    private lateinit var SerieDetails: TvDetails
    private lateinit var listaSemelhantes: SimilarMovies
    lateinit var serieAdapter: MediaEspecificoSerieAdapter
    lateinit var movieAdapter: MediaEspecificoMovieAdapter

    private val viewModel by viewModels<EspecificoFragmentViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(service) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (Movie == true) {
            val view: View = inflater!!.inflate(R.layout.fragment_series_seasons, container, false)
            viewModel.getSimilarMovies((MediaSelect as ResultMovie).id.toString())
            viewModel.listSimilar.observe(viewLifecycleOwner) {
                listaSemelhantes = it
                var adapter = MediaEspecificoMovieAdapter(listaSemelhantes, this, Movie)
                view.rv_temporada.adapter = adapter
                view.rv_temporada.layoutManager = GridLayoutManager(activity, 2)
                view.rv_temporada.setHasFixedSize(true)
            }
        } else {
            val view: View = inflater!!.inflate(R.layout.fragment_series_seasons, container, false)
            viewModel.getDetailsSerie((MediaSelect as ResultTv).id.toString())
            viewModel.listDetails.observe(viewLifecycleOwner) {
                SerieDetails = it
                val adapter = MediaEspecificoSerieAdapter(SerieDetails, this)
                view.rv_temporada.adapter = adapter
                view.rv_temporada.layoutManager = GridLayoutManager(activity, 2)
                view.rv_temporada.setHasFixedSize(true)
            }
        }
        return view

    }

    override fun SeriemediaClick(position: Int) {
        val serie = SerieDetails.seasons.get(position)
        serieAdapter.notifyItemChanged(position)
    }

    override fun MoviemediaClick(position: Int) {
        val movie = listaSemelhantes.results.get(position)
        movieAdapter.notifyItemChanged(position)
    }


}