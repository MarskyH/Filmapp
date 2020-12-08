package com.example.filmapp.Home.fragments

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
import com.example.filmapp.Home.Adapters.RecyclerViews.DescubraTVAdapter
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.fragments.viewmodels.MelhoresFilmesViewModel
import com.example.filmapp.home.fragments.viewmodels.MelhoresSeriesViewModel
import kotlinx.android.synthetic.main.fragment_melhores_filmes.view.*
import kotlinx.android.synthetic.main.fragment_melhores_series.view.*

class MelhoresSeriesFragment : Fragment(), DescubraTVAdapter.onDescubraTVClickListener {

    private lateinit var melhoresSeriesAdapter: DescubraTVAdapter
    private lateinit var melhoresSeriesLayoutManager: RecyclerView.LayoutManager

    val viewModel by viewModels<MelhoresSeriesViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MelhoresSeriesViewModel(service) as T
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
        val view = inflater.inflate(R.layout.fragment_melhores_series, container, false)

        //Iniciando o ReciclerView de Melhores Séries
        melhoresSeriesLayoutManager = LinearLayoutManager(context)
        melhoresSeriesAdapter = DescubraTVAdapter(this)
        view.rc_melhoresSeriesList.layoutManager = melhoresSeriesLayoutManager
        view.rc_melhoresSeriesList.adapter = melhoresSeriesAdapter
        view.rc_melhoresSeriesList.isHorizontalFadingEdgeEnabled
        view.rc_melhoresSeriesList.setHasFixedSize(true)

        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            melhoresSeriesAdapter.addList(mediaList)
        }

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
        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            val serie = mediaList.get(position)

            val intent = Intent(context, MediaSelectedActivity::class.java)
            startActivity(intent)
        }
    }

}