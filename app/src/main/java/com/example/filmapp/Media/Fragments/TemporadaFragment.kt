package com.example.filmapp.Series.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.filmapp.Entities.TV.Season
import com.example.filmapp.R
import com.example.filmapp.Series.Ui.SerieTemporadaActivity
import kotlinx.android.synthetic.main.fragment_series_midea.*
import kotlinx.android.synthetic.main.fragment_series_midea.view.*
import kotlinx.android.synthetic.main.fragment_series_temporada.view.*


class TemporadaFragment(val id: Int?, val season: Season?, val poster_path: String? ) : Fragment()  {


    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater!!.inflate(R.layout.fragment_series_temporada, container, false)

        return view
    }


}