package com.example.filmapp.Home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Classes.Media
import com.example.filmapp.Home.Adapters.RecyclerViews.DescubraListsAdapter
import com.example.filmapp.R
import kotlinx.android.synthetic.main.activity_descubra.*
import kotlinx.android.synthetic.main.fragment_melhores_filmes.view.*

class MelhoresFilmesFragment : Fragment() {
    private val mediaList = getMediaList()
    private val adapter = DescubraListsAdapter(mediaList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_melhores_filmes, container, false)

        //Iniciando o ReciclerView Séries
        view.rc_melhoresFilmesList.adapter = adapter
        view.rc_melhoresFilmesList.layoutManager = LinearLayoutManager(context)
        view.rc_melhoresFilmesList.isHorizontalFadingEdgeEnabled
        view.rc_melhoresFilmesList.setHasFixedSize(true)

        return view
    }

    fun getMediaList(): ArrayList<Media>{
        return arrayListOf<Media>(
            Media(1,R.drawable.fear_image01,"The Fear Walking Dead", "Filme", "", "Quando: 08/08/12", "Onde:Cinemas", "02h 55min", "Policial, Drama"),
            Media(1,R.drawable.the_boys_image01,"The Boys", "Filme", "", "Quando: 21/08/18", "Onde: Amazon", "02h 55min", "Policial, Drama"),
        )
    }

}