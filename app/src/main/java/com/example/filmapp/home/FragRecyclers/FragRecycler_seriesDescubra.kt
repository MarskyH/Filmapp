package com.example.filmapp.Home.FragRecyclers

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Media
import com.example.filmapp.Home.Adapters.RecyclerViews.DescubraListsAdapter
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.FragRecyclers.viewmodels.FilmesDescubraViewModel
import com.example.filmapp.home.FragRecyclers.viewmodels.SeriesDescubraViewModel
import kotlinx.android.synthetic.main.fragrecycler_filmesdescubra.view.*
import kotlinx.android.synthetic.main.fragrecycler_seriesdescubra.view.*

class FragRecycler_seriesDescubra : Fragment(), DescubraListsAdapter.onDescubraItemClickListener {

    private lateinit var mediaListAdapter: DescubraListsAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager

    val viewModel by viewModels<SeriesDescubraViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SeriesDescubraViewModel(service) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragrecycler_seriesdescubra, container, false)

        //Iniciando o ReciclerView Descubra - Series
        mediaListLayoutManager = LinearLayoutManager(context)
        mediaListAdapter = DescubraListsAdapter()
        view.rv_seriesDescubra.layoutManager = mediaListLayoutManager
        view.rv_seriesDescubra.adapter = mediaListAdapter
        view.rv_seriesDescubra.isHorizontalFadingEdgeEnabled
        view.rv_seriesDescubra.setHasFixedSize(true)

        viewModel.returnDescubraSeriesListAPI.observe(this){
            mediaListAdapter.addList(it)
        }


        return view
    }

    companion object{
        fun newInstance() = FragRecycler_seriesDescubra()
    }

    fun getSeriesList(): ArrayList<Media>{
        return arrayListOf<Media>(
            Media(1,R.drawable.academy_image01,"The Umbrella Academy", "Série", "2x08 - O Que Eu Sei", "Quando: 21/08/12", "Onde: Netflix", "4 Temporadas", "37 Episodeos"),
            Media(1,R.drawable.flash_image01,"The Flash", "Série", "2x08 - O Que Eu Sei", "Quando: 21/09/12", "Onde: Netflix", "3 Temporadas", "7 Episodeos"),
            Media(1,R.drawable.grey_image01,"Grey's Anatomy", "Série", "2x08 - O Que Eu Sei", "Quando: 75/08/12", "Onde: Netflix", "1 Temporadas", "10 Episodeos")
        )
    }

    override fun descubraItemClick(position: Int) {
        val serie = seriesList.get(position)

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