package com.example.filmapp.home.melhores

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
import com.example.filmapp.home.descubra.DescubraSeriesAdapter
import kotlinx.android.synthetic.main.fragment_melhores_series.view.*

class MelhoresSeriesFragment : Fragment(), MelhoresSeriesAdapter.onMelhoresSerieClickListener {

    private lateinit var melhoresSeriesAdapter: MelhoresSeriesAdapter
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
        melhoresSeriesAdapter = MelhoresSeriesAdapter(this)
        view.rc_melhoresSeriesList.layoutManager = melhoresSeriesLayoutManager
        view.rc_melhoresSeriesList.adapter = melhoresSeriesAdapter
        view.rc_melhoresSeriesList.isHorizontalFadingEdgeEnabled
        view.rc_melhoresSeriesList.setHasFixedSize(true)

        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            melhoresSeriesAdapter.addList(mediaList)
        }

        viewModel.getTopSeriesList()

        return view
    }

    override fun melhoresItemClick(position: Int) {
        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            val serie = mediaList.get(position)

            val intent = Intent(context, MediaSelectedActivity::class.java)
            startActivity(intent)
        }
    }

}