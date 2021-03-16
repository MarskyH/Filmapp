package com.example.filmapp.home.agenda

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragrecycler_assistirmaistarde.*
import kotlinx.android.synthetic.main.fragrecycler_proximosagenda.*
import kotlinx.android.synthetic.main.fragrecycler_proximosagenda.view.*

class FragRecycler_proximosAgenda : Fragment(), ProximosAdapter.onProximosItemClickListener {

    private lateinit var mediaListAdapter: ProximosAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager
    var novosEpisodiosList = arrayListOf<ResultTv>()

    val viewModel by viewModels<ProximosAgendaViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ProximosAgendaViewModel(service) as T
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
        var view = inflater.inflate(R.layout.fragrecycler_proximosagenda, container, false)

        //Iniciando o ReciclerView Próximos da AgendaPage
        mediaListLayoutManager = LinearLayoutManager(context)
        mediaListAdapter = ProximosAdapter(this)
        view.rv_proximos_agenda.layoutManager = mediaListLayoutManager
        view.rv_proximos_agenda.adapter = mediaListAdapter
        view.rv_proximos_agenda.isHorizontalFadingEdgeEnabled
        view.rv_proximos_agenda.setHasFixedSize(true)

        viewModel.returnNovosEpisodiosListAPI.observe(viewLifecycleOwner) {
            novosEpisodiosList = it.results
            viewModel.getAcompanhadoList()
        }

        //Recebendo os Itens (Filmes e Séries) que estão sendo acompanhados pelo usuário
        viewModel.returnAcompanhandoList.observe(viewLifecycleOwner) {
            var filteredList = viewModel.checkEpisodiosForUser(novosEpisodiosList, it)
            if(filteredList.size == 0){
                pb_proximos.setVisibility(View.INVISIBLE)
            }else {
                filteredList.forEach {
                    viewModel.getDetailsSerie(it)
                }
            }
        }

        viewModel.detaisSerie.observe(viewLifecycleOwner) {
            pb_proximos.setVisibility(View.INVISIBLE)
            mediaListAdapter.addList(it)
        }

        viewModel.getNovosEpisodiosList()

        return view
    }

    companion object {
        fun newInstance() = FragRecycler_proximosAgenda()
    }

    override fun proximosItemClick(position: Int) {
        var mediaList = mediaListAdapter.mediaList
        var media = mediaList.get(position)

        val intent = Intent(context, MediaSelectedActivity::class.java)
        intent.putExtra("poster", media.poster_path)

        if (media.type == "Movie")
            intent.putExtra("movie", true)
        else
            intent.putExtra("movie", false)

        intent.putExtra("id", media.id)

        startActivity(intent)
    }

}