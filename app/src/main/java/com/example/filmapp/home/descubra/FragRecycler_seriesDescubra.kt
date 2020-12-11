package com.example.filmapp.home.descubra

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
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.main.fragrecycler_seriesdescubra.view.*

class FragRecycler_seriesDescubra : Fragment(), DescubraSeriesAdapter.onDescubraSerieClickListener {

    private lateinit var mediaListAdapter: DescubraSeriesAdapter
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
        mediaListAdapter = DescubraSeriesAdapter(this)
        view.rv_seriesDescubra.layoutManager = mediaListLayoutManager
        view.rv_seriesDescubra.adapter = mediaListAdapter
        view.rv_seriesDescubra.isHorizontalFadingEdgeEnabled
        view.rv_seriesDescubra.setHasFixedSize(true)

        viewModel.returnDescubraSeriesListAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            mediaListAdapter.addList(mediaList)
        }

//        viewModel.getDescubraSeriesList() //Como implementar isso??

        return view
    }

    companion object{
        fun newInstance() = FragRecycler_seriesDescubra()
    }

    override fun descubraItemClick(position: Int) {
        viewModel.returnDescubraSeriesListAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            var serie = mediaList.get(position)

            val intent = Intent(context, MediaSelectedActivity::class.java)
            startActivity(intent)
        }
    }

}