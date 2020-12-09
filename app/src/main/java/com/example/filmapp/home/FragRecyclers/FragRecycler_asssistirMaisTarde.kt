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
import com.example.filmapp.home.FragRecyclers.viewmodels.AssistirMaisTardeViewModel
import kotlinx.android.synthetic.main.fragrecycler_assistirmaistarde.view.*

class FragRecycler_asssistirMaisTarde : Fragment(), AssistirMaisTardeAdapter.onAssistirMaisTardeItemClickListener {

    private lateinit var mediaListAdapter: AssistirMaisTardeAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager

    val viewModel by viewModels<AssistirMaisTardeViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AssistirMaisTardeViewModel(service) as T
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
        var view = inflater.inflate(R.layout.fragrecycler_assistirmaistarde, container, false)

        //Iniciando o ReciclerView Assistir Mais Tarde
        mediaListLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mediaListAdapter = AssistirMaisTardeAdapter(this)
        view.rv_assistirMaisTarde.layoutManager = mediaListLayoutManager
        view.rv_assistirMaisTarde.adapter = mediaListAdapter
        view.rv_assistirMaisTarde.isHorizontalFadingEdgeEnabled
        view.rv_assistirMaisTarde.setHasFixedSize(true)

//        viewModel.returnUserAssistirMaisTardeAPI.observe(this){
//            mediaListAdapter.addList(it)
//        }

        return view
    }

    companion object{
        fun newInstance() = FragRecycler_asssistirMaisTarde()
    }

    override fun assistirMaisTardeItemClick(position: Int) {
//        val media = mediaList.get(position)

        val intent = Intent(context, MediaSelectedActivity::class.java)
        startActivity(intent)
    }

}