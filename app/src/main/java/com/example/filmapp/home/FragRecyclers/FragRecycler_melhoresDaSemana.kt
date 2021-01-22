package com.example.filmapp.home.FragRecyclers

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
import com.example.filmapp.home.FragRecyclers.viewmodels.MelhoresDaSemanaViewModel
import kotlinx.android.synthetic.main.fragrecycler_melhoresdasemana.view.*

class FragRecycler_melhoresDaSemana : Fragment(),
    MelhoresDaSemanaAdapter.onMelhoresDaSemanaItemClickListener {

    private lateinit var mediaListAdapter: MelhoresDaSemanaAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager

    val viewModel by viewModels<MelhoresDaSemanaViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MelhoresDaSemanaViewModel(service) as T
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
        val view = inflater.inflate(R.layout.fragrecycler_melhoresdasemana, container, false)
        viewModel.returnMelhoresDaSemanaListAPI.observe(viewLifecycleOwner) {
            val mediaList = it
            mediaListLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            mediaListAdapter = MelhoresDaSemanaAdapter(this, mediaList)
            view.rv_melhoresDaSemana_EmAlta.layoutManager = mediaListLayoutManager
            view.rv_melhoresDaSemana_EmAlta.adapter = mediaListAdapter
            view.rv_melhoresDaSemana_EmAlta.isHorizontalFadingEdgeEnabled
            view.rv_melhoresDaSemana_EmAlta.setHasFixedSize(true)
        }
//        viewModel.getMelhoresDaSemanaList()

        return view
    }

    companion object {
        fun newInstance() = FragRecycler_melhoresDaSemana()
    }

    override fun melhoresDaSemanaItemClick(position: Int) {
        viewModel.returnMelhoresDaSemanaListAPI.observe(viewLifecycleOwner) {
////            val mediaList = it.results
//            var media = mediaList.get(position)
//            mediaListAdapter.notifyDataSetChanged()
        }
    }

}