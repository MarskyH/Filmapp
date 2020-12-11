package com.example.filmapp.Series.Fragments


import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.filmapp.Entities.TV.Season
import com.example.filmapp.Media.Fragments.HomeMediaFragment

import com.example.filmapp.R

import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.fragment_series_temporada.view.*
import java.io.Serializable


class TemporadaFragment(): Fragment()  {

    val picasso = Picasso.get()
    var Season: Any? = null
    var Poster_path: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
           Season = arguments?.getSerializable(season)
            Poster_path = arguments?.getString(poster_path)
        }
    }

    companion object{
        private val season = "season"
        private val poster_path = "poster_path"

        fun newInstance(Season: Serializable?, Poster_path: String?): TemporadaFragment {
            val fragment = TemporadaFragment()
            val args = Bundle()
            args.putSerializable(season, Season)
            args.putString(poster_path, Poster_path)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater!!.inflate(R.layout.fragment_series_temporada, container, false)
        picasso.load(Poster_path).into(view.imgTemporada)
        Log.i("poster temporada", Poster_path.toString())
        view.textViewSinopseTemporada.text = (Season as Season).overview
        return view
    }


}