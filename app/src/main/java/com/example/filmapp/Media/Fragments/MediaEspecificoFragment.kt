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
import com.example.filmapp.Entities.APIConfig.Config
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


class MediaEspecificoFragment (val Movie: Boolean, var MediaSelect: Any?) : Fragment(),
    MediaEspecificoSerieAdapter.OnMediaSerieClickListener,
    MediaEspecificoMovieAdapter.OnMediaMovieClickListener {
    private lateinit var SerieDetails: TvDetails
    private lateinit var listaSemelhantes: SimilarMovies
    lateinit var serieAdapter: MediaEspecificoSerieAdapter
    lateinit var movieAdapter: MediaEspecificoMovieAdapter
    lateinit var config: Config


    private val viewModelEspecificoFragment by viewModels<EspecificoFragmentViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return EspecificoFragmentViewModel(service) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_series_seasons, container, false)
        var idMovie = ((MediaSelect as? ResultMovie)?.id).toString()
        if (Movie == true) {
            viewModelEspecificoFragment.config.observe(viewLifecycleOwner){
                config = it
            }
            viewModelEspecificoFragment.getConfig()
            viewModelEspecificoFragment.listSimilar.observe(viewLifecycleOwner) {
                listaSemelhantes = it
                var adapter = MediaEspecificoMovieAdapter(listaSemelhantes, this, Movie, config)
                view?.rv_temporada.adapter = adapter
                view?.rv_temporada.layoutManager = GridLayoutManager(activity, 2)
                view?.rv_temporada.setHasFixedSize(true)
            }
            viewModelEspecificoFragment.getSimilarMovies(idMovie)
        } else {
            viewModelEspecificoFragment.config.observe(viewLifecycleOwner){
                config = it
            }
            viewModelEspecificoFragment.getConfig()
            viewModelEspecificoFragment.getDetailsSerie((MediaSelect as ResultTv).id.toString())
            viewModelEspecificoFragment.listDetails.observe(viewLifecycleOwner) {
                SerieDetails = it
                val adapter = MediaEspecificoSerieAdapter(SerieDetails, this, config)
                view?.rv_temporada.adapter = adapter
                view?.rv_temporada.layoutManager = GridLayoutManager(activity, 2)
                view?.rv_temporada.setHasFixedSize(true)
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