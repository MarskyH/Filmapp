package com.example.filmapp.Home.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Classes.Media
import com.example.filmapp.Home.Adapters.RecyclerViews.DescubraListsAdapter

import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import kotlinx.android.synthetic.main.fragment_melhores_series.view.*

class MelhoresSeriesFragment : Fragment(), DescubraListsAdapter.onDescubraItemClickListener {
    private val mediaList = getMediaList()
    private val adapter = DescubraListsAdapter(mediaList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_melhores_series, container, false)

        //Iniciando o ReciclerView Séries
        view.rc_melhoresSeriesList.adapter = adapter
        view.rc_melhoresSeriesList.layoutManager = LinearLayoutManager(context)
        view.rc_melhoresSeriesList.isHorizontalFadingEdgeEnabled
        view.rc_melhoresSeriesList.setHasFixedSize(true)

        return view
    }

    fun getMediaList(): ArrayList<Media>{
        return arrayListOf<Media>(
            Media(1,R.drawable.academy_image01,"The Umbrella Academy", "Série", "2x08 - O Que Eu Sei", "Quando: 21/08/12", "Onde: Netflix", "4 Temporadas", "37 Episodeos"),
            Media(1,R.drawable.flash_image01,"The Flash", "Série", "2x08 - O Que Eu Sei", "Quando: 21/09/12", "Onde: Netflix", "3 Temporadas", "7 Episodeos"),
            Media(1,R.drawable.grey_image01,"Grey's Anatomy", "Série", "2x08 - O Que Eu Sei", "Quando: 75/08/12", "Onde: Netflix", "1 Temporadas", "10 Episodeos")
        )
    }

    override fun descubraItemClick(position: Int) {
        val serie = mediaList.get(position)


        val intent = Intent(context, MediaSelectedActivity::class.java)
        startActivity(intent)
    }

//    override fun assistirMaisTardeIndicationClick(position: Int) {
//        Toast.makeText(context, "Clicou no Assistir Mais Tarde", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun evaluationIndicationClick(position: Int) {
//        Toast.makeText(context, "Clicou na Avaliação", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun shareIndicationIndicationClick(position: Int) {
//        Toast.makeText(context, "Clicou em Compartilhar", Toast.LENGTH_SHORT).show()
//    }

}