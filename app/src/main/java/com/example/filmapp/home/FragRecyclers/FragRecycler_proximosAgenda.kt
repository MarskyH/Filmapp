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
import com.example.filmapp.Home.Adapters.RecyclerViews.*
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.FragRecyclers.viewmodels.AssistirMaisTardeViewModel
import com.example.filmapp.home.FragRecyclers.viewmodels.ProximosAgendaViewModel
import kotlinx.android.synthetic.main.fragrecycler_assistirmaistarde.view.*
import kotlinx.android.synthetic.main.fragrecycler_proximosagenda.view.*
class FragRecycler_proximosAgenda : Fragment(), ProximosAdapter.onProximosItemClickListener {

    private lateinit var mediaListAdapter: ProximosAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager

    val viewModel by viewModels<ProximosAgendaViewModel>{
        object : ViewModelProvider.Factory{
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
        mediaListAdapter = ProximosAdapter()
        view.rv_proximos_agenda.layoutManager = mediaListLayoutManager
        view.rv_proximos_agenda.adapter = mediaListAdapter
        view.rv_proximos_agenda.isHorizontalFadingEdgeEnabled
        view.rv_proximos_agenda.setHasFixedSize(true)

        viewModel.returnProximosListAPI.observe(this){
            mediaListAdapter.addList(it)
        }

        return view
    }

    companion object{
        fun newInstance() = FragRecycler_proximosAgenda()
    }

    fun getMediaList(): ArrayList<Media>{
        return arrayListOf<Media>(
            Media(1,R.drawable.academy_image01,"The Umbrella Academy", "Série", "2x08 - O Que Eu Sei", "Data: 21/08/12", "Onde: Netflix", "4 Temporadas", "37 Episodeos"),
            Media(1,R.drawable.fear_image01,"The Fear Walking Dead", "Filme", "", "Data: 08/08/12", "Onde: Amazon", "8 Temporadas", "2 Episodeos"),
            Media(1,R.drawable.flash_image01,"The Flash", "Série", "2x08 - O Que Eu Sei", "Data: 21/09/12", "Onde: Netflix", "3 Temporadas", "7 Episodeos"),
            Media(1,R.drawable.the_boys_image01,"The Boys", "Filme", "", "Data: 21/08/18", "Onde: Amazon", "7 Temporadas", "87 Episodeos"),
            Media(1,R.drawable.grey_image01,"Grey's Anatomy", "Série", "2x08 - O Que Eu Sei", "Data: 75/08/12", "Onde: Netflix", "1 Temporadas", "10 Episodeos"))
    }

    override fun proximosItemClick(position: Int) {
        val media = mediaList.get(position)

        val intent = Intent(context, MediaSelectedActivity::class.java)
        startActivity(intent)
    }

}