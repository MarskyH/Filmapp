package com.example.filmapp.Media.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Media.Adapters.MediaAdapter
import com.example.filmapp.R
import kotlinx.android.synthetic.main.fragment_media_medias.view.*
import kotlinx.android.synthetic.main.fragment_series_midea.*
import kotlinx.android.synthetic.main.fragment_series_midea.view.video_view


class MediaFragment : Fragment(), MediaAdapter.OnMideaClickListener {
    var listaMideas = getAllMideas()
    var adapter = MediaAdapter(listaMideas, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater!!.inflate(R.layout.fragment_media_medias, container, false)
        view.rv_medias.adapter = adapter
        view.rv_medias.layoutManager =
            GridLayoutManager(activity, 1, LinearLayoutManager.VERTICAL, false)
        view.rv_medias.setHasFixedSize(true)
        val mediaController = MediaController(activity)
        view.video_view.setVideoPath("android.resource://${activity?.packageName}/${R.raw.video}")
        mediaController.setAnchorView(video_view)
        view.video_view.setMediaController(mediaController)
        return view

    }

    fun getAllMideas(): ArrayList<com.example.filmapp.Classes.Media> {
        return arrayListOf(
            com.example.filmapp.Classes.Media(
                1,
                R.drawable.fim,
                "2012",
                "Série",
                "2x08 - O Que Eu Sei",
                "21/08/12",
                "Netflix",
                "4 Temporadas",
                "Sinopse de 2012"
            ),
            com.example.filmapp.Classes.Media(
                1,
                R.drawable.star_born,
                "Nasce um Estrela",
                "Filme",
                "",
                "08/08/12",
                "Amazon",
                "8 Temporadas",
                "Sinopse de Star"
            ),
            com.example.filmapp.Classes.Media(
                1,
                R.drawable.star_wars,
                "Star Wars: A Ascensão Skywalker",
                "Série",
                "2x08 - O Que Eu Sei",
                "21/09/12",
                "Netflix",
                "3 Temporadas",
                "Sinopse de Star wars"
            ),
            com.example.filmapp.Classes.Media(
                1,
                R.drawable.regresso,
                "O Regresso",
                "Filme",
                "",
                "21/08/18",
                "Amazon",
                "7 Temporadas",
                "Sinopse de O Regresso"
            ),
            com.example.filmapp.Classes.Media(
                1,
                R.drawable.animais,
                "Animais Fantásticos e os Crimes de Grindelwald",
                "Série",
                "2x08 - O Que Eu Sei",
                "75/08/12",
                "Netflix",
                "1 Temporadas",
                "Sinopse de Animais Fantásticos "
            ),
            com.example.filmapp.Classes.Media(
                1,
                R.drawable.ultimato,
                "Vingadores: Ultimato",
                "Série",
                "2x08 - O Que Eu Sei",
                "75/08/12",
                "Netflix",
                "1 Temporadas",
                "Sinopse de Vingadores Ultimato"
            )
        )
    }

    override fun mideaClick(position: Int) {
        val media = listaMideas.get(position)
        Toast.makeText(activity, "Deu certo", Toast.LENGTH_SHORT).show()
        adapter.notifyItemChanged(position)
    }


}