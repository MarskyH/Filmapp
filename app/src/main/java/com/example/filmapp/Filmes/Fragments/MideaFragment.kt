package com.example.filmapp.Filmes.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.R
import com.example.filmapp.Series.Adapter.MideaAdapter
import com.example.filmapp.Series.Classes.Media
import kotlinx.android.synthetic.main.fragment_filmes_midea.*
import kotlinx.android.synthetic.main.fragment_filmes_midea.view.*


class MideaFragment : Fragment(), MideaAdapter.OnMideaClickListener {
    var listaMideas = getAllMideas()
    var adapter = MideaAdapter(listaMideas, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater!!.inflate(R.layout.fragment_filmes_midea, container, false)
        view.rv_mideas_filmes.adapter = adapter
        view.rv_mideas_filmes.layoutManager =
            GridLayoutManager(activity, 1, LinearLayoutManager.VERTICAL, false)
        view.rv_mideas_filmes.setHasFixedSize(true)
        val mediaController = MediaController(activity)
        view.video_view_filmes.setVideoPath("android.resource://${activity?.packageName}/${R.raw.trailer_fim}")
        mediaController.setAnchorView(video_view_filmes)
        view.video_view_filmes.setMediaController(mediaController)
        return view

    }

    fun getAllMideas(): ArrayList<Media> {
        val midea1 = Media(1, R.drawable.fim1)
        val midea2 = Media(2, R.drawable.fim2)
        val midea3 = Media(3, R.drawable.fim3)
        val midea4 = Media(4, R.drawable.fim4)
        return arrayListOf(midea1, midea2, midea3, midea4)
    }

    override fun mideaClick(position: Int) {
        val midea = listaMideas.get(position)
        adapter.notifyItemChanged(position)
    }


}