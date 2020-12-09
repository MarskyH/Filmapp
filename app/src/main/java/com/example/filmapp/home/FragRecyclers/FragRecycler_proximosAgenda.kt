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
import com.example.filmapp.home.FragRecyclers.viewmodels.ProximosAgendaViewModel
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
        mediaListAdapter = ProximosAdapter(this)
        view.rv_proximos_agenda.layoutManager = mediaListLayoutManager
        view.rv_proximos_agenda.adapter = mediaListAdapter
        view.rv_proximos_agenda.isHorizontalFadingEdgeEnabled
        view.rv_proximos_agenda.setHasFixedSize(true)

//        viewModel.returnProximosListAPI.observe(this){
//            mediaListAdapter.addList(it)
//        }

        return view
    }

    companion object{
        fun newInstance() = FragRecycler_proximosAgenda()
    }

    override fun proximosItemClick(position: Int) {
//        val media = mediaList.get(position)

        val intent = Intent(context, MediaSelectedActivity::class.java)
        startActivity(intent)
    }

}