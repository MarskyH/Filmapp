package com.example.filmapp.home.agenda

import android.content.Intent
import android.os.Bundle
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
import com.example.filmapp.home.acompanhando.AcompanhandoDataBaseViewModel
import com.example.filmapp.home.acompanhando.dataBase.AcompanhandoEntity
import kotlinx.android.synthetic.main.fragrecycler_assistirmaistarde.*
import kotlinx.android.synthetic.main.fragrecycler_proximosagenda.*
import kotlinx.android.synthetic.main.fragrecycler_proximosagenda.view.*

class FragRecycler_proximosAgenda : Fragment(), ProximosAdapter.onProximosItemClickListener {

    private lateinit var mediaListAdapter: ProximosAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager
    private lateinit var viewModelAcompanhando: AcompanhandoDataBaseViewModel
    lateinit var listAcompanhandoDataBase: List<AcompanhandoEntity>
    lateinit var listAPI: ArrayList<ResultTv>

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

        viewModelAcompanhando =
            ViewModelProvider(this).get(AcompanhandoDataBaseViewModel::class.java)

        //Iniciando o ReciclerView Próximos da AgendaPage
        mediaListLayoutManager = LinearLayoutManager(context)
        mediaListAdapter = ProximosAdapter(this)
        view.rv_proximos_agenda.layoutManager = mediaListLayoutManager
        view.rv_proximos_agenda.adapter = mediaListAdapter
        view.rv_proximos_agenda.isHorizontalFadingEdgeEnabled
        view.rv_proximos_agenda.setHasFixedSize(true)

        //Recebendo os Itens (Filmes e Séries) que estão sendo acompanhados pelo usuário
        viewModelAcompanhando.mediaList.observe(viewLifecycleOwner) {
            listAcompanhandoDataBase = it
        }

        viewModel.returnNovosEpisodiosListAPI.observe(viewLifecycleOwner) {
            var filteredList = viewModel.checkEpisodiosForUser(it.results, listAcompanhandoDataBase)
            filteredList.forEach {
                viewModel.getDetailsSerie(it)
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
        val media = mediaList.get(position)

    }

}