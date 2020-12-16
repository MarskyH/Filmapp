package com.example.filmapp.home.descubra

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
import com.example.filmapp.Entities.All.BaseSearchAll
import com.example.filmapp.Entities.All.ResultSearchAll
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.Movie.SearchMovie
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Entities.TV.SearchTv
import com.example.filmapp.Media.Adapters.HomeMediaMovieAdapter
import com.example.filmapp.Media.Adapters.HomeMediaSerieAdapter
import com.example.filmapp.R
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.main.fragment_home_media.view.*
import kotlinx.android.synthetic.main.fragrecycler_descubra.view.*


class DescubraFragment: Fragment(), DescubraMovieAdapter.DescubraMovieClickListener,
    DescubraSerieAdapter.DescubraSerieClickListener {
    private lateinit var MovieAdapter: DescubraMovieAdapter
    private lateinit var SerieAdapter: DescubraSerieAdapter
    private lateinit var lManager: LinearLayoutManager
    lateinit var ListMediaDescubraMovie: SearchMovie
    lateinit var ListMediaDescubraTv: SearchTv

    var Movie: Boolean? = null
    var Text: String? = null

    var config = Config()


    private val viewModelDescubra by viewModels<DescubraViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DescubraViewModel(service) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            Movie = arguments?.getBoolean(movie)
            Text = arguments?.getString(text)
        }
    }

    companion object{
        private val movie = "movie"
        private val text = "text"

        fun newInstance(Movie: Boolean, Text: String): DescubraFragment{
            val fragment = DescubraFragment()
            val args = Bundle()
            args.putBoolean(movie, Movie)
            args.putString(text, Text)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            val view: View = inflater!!.inflate(R.layout.fragrecycler_descubra, container, false)
            if (Movie == true) {
                viewModelDescubra.listDescubraMovie.observe(viewLifecycleOwner) {
                    ListMediaDescubraMovie = it
                    MovieAdapter = DescubraMovieAdapter(ListMediaDescubraMovie, this, Movie, config)
                    lManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    view.rv_Descubra.layoutManager = lManager
                    view.rv_Descubra.adapter = MovieAdapter
                    view.rv_Descubra.setHasFixedSize(true)
                }
                viewModelDescubra.getSearchMovie(Text)
            } else {
                viewModelDescubra.config.observe(viewLifecycleOwner) {
                    config = it
                }
                viewModelDescubra.getConfig()
                viewModelDescubra.listDescubraTv.observe(viewLifecycleOwner) {
                    ListMediaDescubraTv = it
                    SerieAdapter = DescubraSerieAdapter(ListMediaDescubraTv, this, Movie, config)
                    lManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    view.rv_Descubra.layoutManager = lManager
                    view.rv_Descubra.adapter = SerieAdapter
                    view.rv_Descubra.setHasFixedSize(true)
                }
                viewModelDescubra.getSearchTV(Text)
            }
        return view
    }


    override fun descubraMovieClick(position: Int) {
        if (Movie == true) {
            val media = ListMediaDescubraMovie.results.get(position)
            MovieAdapter.notifyDataSetChanged()
        }
    }

    override fun descubraSerieClick(position: Int) {
        if (Movie == false) {
            val media = ListMediaDescubraTv.results.get(position)
            MovieAdapter.notifyDataSetChanged()
        }
    }


}
