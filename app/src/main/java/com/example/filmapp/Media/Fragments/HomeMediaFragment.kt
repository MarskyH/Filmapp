package com.example.filmapp.Media.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Media.Adapters.HomeMediaMovieAdapter
import com.example.filmapp.Media.Adapters.HomeMediaSerieAdapter
import com.example.filmapp.R
import kotlinx.android.synthetic.main.fragment_home_media.view.*


class HomeMediaFragment(
    val ListMediaMovie: ArrayList<ResultMovie>,
    val ListMediaSerie: ArrayList<ResultTv>,
    val Movie: Boolean
) : Fragment(), HomeMediaMovieAdapter.OnHomeMediaMovieClickListener, HomeMediaSerieAdapter.OnHomeMediaSerieClickListener {
    private lateinit var MovieAdapter: HomeMediaMovieAdapter
    private lateinit var SerieAdapter: HomeMediaSerieAdapter
    private lateinit var lManager: LinearLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view: View = inflater!!.inflate(R.layout.fragment_home_media, container, false)
        if (Movie == true) {
            lManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            MovieAdapter = HomeMediaMovieAdapter(ListMediaMovie, this, Movie)
            view.rv_pop.layoutManager = lManager
            view.rv_pop.adapter = MovieAdapter
            view.rv_pop.setHasFixedSize(true)
        }else{
            lManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            SerieAdapter = HomeMediaSerieAdapter(ListMediaSerie, this, Movie)
            view.rv_pop.layoutManager = lManager
            view.rv_pop.adapter = SerieAdapter
            view.rv_pop.setHasFixedSize(true)
        }
        return view
    }




    override fun homeMediaMovieClick(position: Int) {
        if (Movie == true){
            val media = ListMediaMovie.get(position)
            MovieAdapter.notifyDataSetChanged()
        }

    }

    override fun homeMediaSerieClick(position: Int) {
        if (Movie == false){
            val media = ListMediaMovie.get(position)
            MovieAdapter.notifyDataSetChanged()
        }
    }


}
