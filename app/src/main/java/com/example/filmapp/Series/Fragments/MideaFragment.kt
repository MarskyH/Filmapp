package com.example.filmapp.Series.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.R
import com.example.filmapp.Series.Adapter.MideaAdapter
import com.example.filmapp.Series.Classes.Media
import kotlinx.android.synthetic.main.fragment_series_midea.*
import kotlinx.android.synthetic.main.fragment_series_midea.view.*


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
        var view = inflater!!.inflate(R.layout.fragment_series_midea, container, false)
        view.rv_mideas.adapter = adapter
        view.rv_mideas.layoutManager =
            GridLayoutManager(activity, 1, LinearLayoutManager.VERTICAL, false)
        view.rv_mideas.setHasFixedSize(true)
        val mediaController = MediaController(activity)
        view.video_view.setVideoPath("android.resource://${activity?.packageName}/${R.raw.video}")
        mediaController.setAnchorView(video_view)
        view.video_view.setMediaController(mediaController)
        return view

    }

    fun getAllMideas(): ArrayList<Media> {
        val midea1 = Media(1, R.drawable.midea_the_boys_image01)
        val midea2 = Media(2, R.drawable.midea_the_boys_image02)
        val midea3 = Media(3, R.drawable.midea_the_boys_image03)
        val midea4 = Media(4, R.drawable.midea_the_boys_image04)
        val midea5 = Media(5, R.drawable.midea_the_boys_image01)
        val midea6 = Media(6, R.drawable.midea_the_boys_image02)
        val midea7 = Media(7, R.drawable.midea_the_boys_image03)
        return arrayListOf(midea1, midea2, midea3, midea4, midea5, midea6, midea7)
    }

    override fun mideaClick(position: Int) {
        val midea = listaMideas.get(position)
        Toast.makeText(activity, "Deu certo", Toast.LENGTH_SHORT).show()
        adapter.notifyItemChanged(position)
    }


}