package com.example.filmapp.Media.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Media.Adapters.FavoritosAdapterMovie
import com.example.filmapp.Media.Adapters.FavoritosAdapterSerie
import com.example.filmapp.Media.Adapters.HomeMediaMovieAdapter
import com.example.filmapp.Media.Adapters.HomeMediaSerieAdapter
import com.example.filmapp.Media.Models.FavoritosViewModel
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.Media.dataBase.FavoritosEntity
import com.example.filmapp.R
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.main.fragment_home_media.view.*


class HomeMediaFragment() : Fragment(), HomeMediaMovieAdapter.OnHomeMediaMovieClickListener,
    HomeMediaSerieAdapter.OnHomeMediaSerieClickListener, FavoritosAdapterMovie.FavoritosItemClickListener, FavoritosAdapterSerie.FavoritosItemClickListener {
    private lateinit var MovieAdapter: HomeMediaMovieAdapter
    private lateinit var SerieAdapter: HomeMediaSerieAdapter
    private lateinit var MovieFavAdapter: FavoritosAdapterMovie
    private lateinit var SerieFavAdapter: FavoritosAdapterSerie
    private lateinit var lManager: LinearLayoutManager
    private lateinit var viewModelFav: FavoritosViewModel
    lateinit var ListMediaMovie: ArrayList<ResultMovie>
    lateinit var ListMediaSerie: ArrayList<ResultTv>
    lateinit var ListMediaMovieFav: List<FavoritosEntity>
    lateinit var ListMediaSerieFav: List<FavoritosEntity>


    var Movie: Boolean? = null

    var config = Config()


    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(service) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            Movie = arguments?.getBoolean(movie)
        }
    }

    companion object{
        private val movie = "movie"

        fun newInstance(Movie: Boolean): HomeMediaFragment{
            val fragment = HomeMediaFragment()
            val args = Bundle()
            args.putBoolean(movie, Movie)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            val view: View = inflater!!.inflate(R.layout.fragment_home_media, container, false)
            viewModelFav = ViewModelProvider(this).get(FavoritosViewModel::class.java)

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
                viewModelFav.mediaListMovie.observe(viewLifecycleOwner){
                    ListMediaMovieFav = it
                    Log.i("Favoritos filmes", it.toString())
                    MovieFavAdapter = FavoritosAdapterMovie(this)
                    lManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    MovieFavAdapter.addList(it)
                    view.rv_fav.layoutManager = lManager
                    view.rv_fav.adapter = MovieFavAdapter
                    view.rv_fav.setHasFixedSize(true)
                }
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
                viewModelFav.mediaListSerie.observe(viewLifecycleOwner){
                    ListMediaMovieFav = it
                    Log.i("Favoritos Séries", it.toString())
                    SerieFavAdapter = FavoritosAdapterSerie(this)
                    lManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    SerieFavAdapter.addList(it)
                    view.rv_fav.layoutManager = lManager
                    view.rv_fav.adapter = SerieFavAdapter
                    view.rv_fav.setHasFixedSize(true)
                }
            }
        return view
    }






    override fun homeMediaMovieClick(position: Int) {
        if (Movie == true) {
            val media = ListMediaMovie.get(position)
            val intent = Intent(context, MediaSelectedActivity::class.java)
            intent.putExtra("poster", media.poster_path)
            intent.putExtra("movie", Movie)
            intent.putExtra("sinopse", media.overview)
            intent.putExtra("id", media.id)
            startActivity(intent)
            MovieAdapter.notifyDataSetChanged()
        }


    }

    override fun homeMediaSerieClick(position: Int) {
        if (Movie == false) {
            val media = ListMediaSerie.get(position)
            val intent = Intent(context, MediaSelectedActivity::class.java)
                intent.putExtra("poster", media.poster_path)
                intent.putExtra("movie", Movie)
                intent.putExtra("id", media.id)
                startActivity(intent)
            SerieAdapter.notifyDataSetChanged()
        }
    }

    override fun favoritosItemClick(position: Int) {
        viewModelFav.mediaList.observe(viewLifecycleOwner){
            val media = it.get(position)
        }
    }


}
