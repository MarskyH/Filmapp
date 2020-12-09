package com.example.filmapp.Series.Fragments


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.filmapp.Entities.TV.Season

import com.example.filmapp.R

import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.fragment_series_temporada.view.*


class TemporadaFragment(val id: Int?, val season: Season?, val poster_path: String? ) : Fragment()  {

    val picasso = Picasso.get()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater!!.inflate(R.layout.fragment_series_temporada, container, false)
        picasso.load(poster_path).into(view.imgTemporada)
        view.textViewSinopseTemporada.text = season?.overview

        return view
    }


}