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
import kotlinx.android.synthetic.main.fragment_melhores_filmes.*
import kotlinx.android.synthetic.main.fragrecycler_assistirmaistarde.*
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

        //Iniciando o ReciclerView Assistir Mais Tarde
        mediaListLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mediaListAdapter = AssistirMaisTardeAdapter(this)
        view.rv_assistirMaisTarde.layoutManager = mediaListLayoutManager
        view.rv_assistirMaisTarde.adapter = mediaListAdapter
        view.rv_assistirMaisTarde.isHorizontalFadingEdgeEnabled
        view.rv_assistirMaisTarde.setHasFixedSize(true)

        viewModel.mediaList.observe(viewLifecycleOwner){
            if(it.size == 0){
                pb_assistirMaisTarde.setVisibility(View.GONE)
//                tv_titleAssistirMaisTarde.setVisibility(View.GONE)
            }else{
                pb_assistirMaisTarde.setVisibility(View.INVISIBLE)
                mediaListAdapter.addList(it)
            }
        }

        return view
    }

    companion object{
        fun newInstance() = FragRecycler_asssistirMaisTarde()
    }

    override fun assistirMaisTardeItemClick(position: Int) {
        viewModel.mediaList.observe(viewLifecycleOwner) {
            var media = it.get(position)

            val intent = Intent(context, MediaSelectedActivity::class.java)
            intent.putExtra("poster", "https://image.tmdb.org/t/p/w500" + media.poster_path)

            if(media.type == "Movie")
                intent.putExtra("movie", true)
            else
                intent.putExtra("movie", false)

            intent.putExtra("id", media.id)

            startActivity(intent)
        }
    }

}