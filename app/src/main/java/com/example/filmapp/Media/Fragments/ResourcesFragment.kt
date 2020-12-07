package com.example.filmapp.Media.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Entities.Movie.ImagesMovie
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.Movie.SimilarMovies
import com.example.filmapp.Entities.TV.ImagesTv
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.Media.Adapters.MediaEspecificoMovieAdapter
import com.example.filmapp.Media.Adapters.MediaEspecificoSerieAdapter
import com.example.filmapp.Media.Adapters.ResourceMovieAdapter
import com.example.filmapp.Media.Adapters.ResourceSerieAdapter
import com.example.filmapp.Media.Models.EspecificoFragmentViewModel
import com.example.filmapp.Media.Models.ResourcesFragmentViewModel
import com.example.filmapp.R
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.main.fragment_media_medias.view.*
import kotlinx.android.synthetic.main.fragment_series_midea.*
import kotlinx.android.synthetic.main.fragment_series_midea.view.video_view
import kotlinx.android.synthetic.main.fragment_series_seasons.view.*


class ResourcesFragment(val mediaSelect: Any?, val Movie: Boolean) : Fragment(),
    ResourceMovieAdapter.OnMideaMovieClickListener, ResourceSerieAdapter.OnMideaSerieClickListener {
    private lateinit var SerieImages: ImagesTv
    private lateinit var MovieImages: ImagesMovie
    lateinit var serieAdapter : ResourceMovieAdapter
    lateinit var movieAdapter: ResourceSerieAdapter


    private val viewModel by viewModels<ResourcesFragmentViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(service) as T
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (Movie == true){
            viewModel.getImagesMovie((mediaSelect as ResultMovie).id.toString())
            viewModel.listImagesMovie.observe(viewLifecycleOwner){
                MovieImages = it
                var adapter = ResourceMovieAdapter(MovieImages, this)
                val view: View = inflater!!.inflate(R.layout.fragment_media_medias, container, false)
                view.rv_medias.adapter = adapter
                view.rv_medias.layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)
                view.rv_medias.setHasFixedSize(true)
            }
        }else{
            viewModel.getImagesSerie((mediaSelect as ResultTv).id.toString())
            viewModel.listImagensSerie.observe(viewLifecycleOwner){
                SerieImages = it
                var adapter = ResourceSerieAdapter(SerieImages, this)
                val view: View = inflater!!.inflate(R.layout.fragment_series_seasons, container, false)
                view.rv_medias.adapter = adapter
                view.rv_medias.layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)
                view.rv_medias.setHasFixedSize(true)
            }
        }
        return  view
//        val mediaController = MediaController(activity)
//        view.video_view.setVideoPath("android.resource://${activity?.packageName}/${R.raw.video}")
//        mediaController.setAnchorView(video_view)
//        view.video_view.setMediaController(mediaController)
    }

    override fun MoviemideaClick(position: Int) {
        val movie = MovieImages.backdrops[position]
        movieAdapter.notifyDataSetChanged()
    }

    override fun SeriemideaClick(position: Int) {
        val serie = SerieImages.backdrops[position]
        serieAdapter.notifyDataSetChanged()
    }



    }




