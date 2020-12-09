package com.example.filmapp.Media.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Media.Adapters.HomeMediaMovieAdapter
import com.example.filmapp.Media.Adapters.HomeMediaSerieAdapter
import com.example.filmapp.R
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.main.fragment_home_media.view.*


class HomeMediaFragment(val Movie: Boolean) : Fragment(), HomeMediaMovieAdapter.OnHomeMediaMovieClickListener,
    HomeMediaSerieAdapter.OnHomeMediaSerieClickListener {
    private lateinit var MovieAdapter: HomeMediaMovieAdapter
    private lateinit var SerieAdapter: HomeMediaSerieAdapter
    private lateinit var lManager: LinearLayoutManager
    lateinit var ListMediaMovie: ArrayList<ResultMovie>
    lateinit var ListMediaSerie: ArrayList<ResultTv>
    var config = Config()


    private val viewModel by viewModels<MainViewModel> {
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


        val view: View = inflater!!.inflate(R.layout.fragment_home_media, container, false)
        if (Movie == true) {
            viewModel.config.observe(viewLifecycleOwner) {
                config = it
            }
            viewModel.getConfig()
            viewModel.listResMovies.observe(viewLifecycleOwner) {
                ListMediaMovie = it.results
                MovieAdapter = HomeMediaMovieAdapter(ListMediaMovie, this, Movie, config)
                lManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                view.rv_pop.layoutManager = lManager
                view.rv_pop.adapter = MovieAdapter
                view.rv_pop.setHasFixedSize(true)
            }
            viewModel.getPopularMovies()
        } else {
            viewModel.config.observe(viewLifecycleOwner) {
                config = it
            }
            viewModel.getConfig()
            viewModel.listResSeries.observe(viewLifecycleOwner) {
                ListMediaSerie = it.results
                SerieAdapter = HomeMediaSerieAdapter(ListMediaSerie, this, Movie, config)
                lManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                view.rv_pop.layoutManager = lManager
                view.rv_pop.adapter = SerieAdapter
                view.rv_pop.setHasFixedSize(true)
            }
            viewModel.getPopularSeries()
        }
        return view
    }

    override fun homeMediaMovieClick(position: Int) {
        if (Movie == true) {
            val media = ListMediaMovie.get(position)
            MovieAdapter.notifyDataSetChanged()
        }

    }

    override fun homeMediaSerieClick(position: Int) {
        if (Movie == false) {
            val media = ListMediaMovie.get(position)
            MovieAdapter.notifyDataSetChanged()
        }
    }




}
