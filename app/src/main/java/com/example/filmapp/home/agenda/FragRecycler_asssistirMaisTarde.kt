package com.example.filmapp.home.agenda

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeEntity
import kotlinx.android.synthetic.main.fragrecycler_assistirmaistarde.view.*

class FragRecycler_asssistirMaisTarde : Fragment(),
    AssistirMaisTardeAdapter.onAssistirMaisTardeItemClickListener {

    private lateinit var mediaListAdapter: AssistirMaisTardeAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager
    private lateinit var viewModel: AssistirMaisTardeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragrecycler_assistirmaistarde, container, false)

        viewModel = ViewModelProvider(this).get(AssistirMaisTardeViewModel::class.java)

//        viewModel.saveNewMedia(AssistirMaisTardeEntity(644479,"The Boys", "", "TV"))
//        viewModel.saveNewMedia(AssistirMaisTardeEntity(496243,"Harry PPPotter", "", "TV"))
//        viewModel.saveNewMedia(AssistirMaisTardeEntity(508442,"Darkkk", "", "TV"))

        //Iniciando o ReciclerView Assistir Mais Tarde
        mediaListLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mediaListAdapter = AssistirMaisTardeAdapter(this)
        view.rv_assistirMaisTarde.layoutManager = mediaListLayoutManager
        view.rv_assistirMaisTarde.adapter = mediaListAdapter
        view.rv_assistirMaisTarde.isHorizontalFadingEdgeEnabled
        view.rv_assistirMaisTarde.setHasFixedSize(true)

        viewModel.mediaList.observe(viewLifecycleOwner){
            mediaListAdapter.addList(it)
        }

        return view
    }

    companion object{
        fun newInstance() = FragRecycler_asssistirMaisTarde()
    }

    override fun assistirMaisTardeItemClick(position: Int) {
        viewModel.mediaList.observe(viewLifecycleOwner){
            val media = it.get(position)
        }
    }

}