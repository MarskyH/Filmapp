package com.example.filmapp.Home.FragRecyclers

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
import com.example.filmapp.Classes.Ajuda
import com.example.filmapp.Home.Adapters.RecyclerViews.AjudaAdapter
import com.example.filmapp.Home.AjudaActivity
import com.example.filmapp.Home.fragments.AjudaDetailsFragment
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.FragRecyclers.viewmodels.DuvidasViewModel
import kotlinx.android.synthetic.main.activity_ajuda.*
import kotlinx.android.synthetic.main.fragrecycler_duvidaslist.view.*
import kotlinx.android.synthetic.main.fragrecycler_novidadeslist.view.*

class FragRecycler_novidadesList : Fragment(), AjudaAdapter.onAjudaItemClickListener {

    private lateinit var novidadesListAdapter: AjudaAdapter
    private lateinit var novidadesListLayoutManager: RecyclerView.LayoutManager

    val viewModel by viewModels<DuvidasViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DuvidasViewModel(service) as T
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
        var view = inflater.inflate(R.layout.fragrecycler_novidadeslist, container, false)

        //Iniciando o ReciclerView Dúvidas Frequentes
        novidadesListLayoutManager = LinearLayoutManager(context)
        novidadesListAdapter = AjudaAdapter()
        view.rc_novidades.layoutManager = novidadesListLayoutManager
        view.rc_novidades.adapter = novidadesListAdapter
        view.rc_novidades.isHorizontalFadingEdgeEnabled
        view.rc_novidades.setHasFixedSize(true)

        viewModel.returnNovidades.observe(this){
            novidadesListAdapter.addList(it)
        }

        return view
    }

    companion object {
        fun newInstance() = FragRecycler_novidadesList()
    }

    fun getNovidadesList(): ArrayList<Ajuda> {
        return arrayListOf<Ajuda>(
            Ajuda(
                0,
                "- O que é o Filmapp?",
                "FilmApp é um aplicativo voltado ao entretenimento com foco em filmes e séries, funcionando como um guia"
            ),
            Ajuda(1, "- O que é o que é?", "Feito para andar e não anda. Resposta: A rua!"),
            Ajuda(
                2,
                "- Onde está Wally?",
                "Where's Wally? é uma série de livros de caráter infanto-juvenil criada pelo ilustrador britânico Martin Handford, baseada em ilustrações e pequenos textos, a série deu origem a uma série animada, uma tira de jornal, uma coleção de 52 revistas semanais intitulada O Mundo de Wally, e jogos eletrônicos."
            ),
            Ajuda(
                3,
                "- Onde está Wally?",
                "Where's Wally? é uma série de livros de caráter infanto-juvenil criada pelo ilustrador britânico Martin Handford, baseada em ilustrações e pequenos textos, a série deu origem a uma série animada, uma tira de jornal, uma coleção de 52 revistas semanais intitulada O Mundo de Wally, e jogos eletrônicos."
            ),
            Ajuda(4, "- Onde está Wally?", "é o ultimo")
        )
    }

    override fun ajudaItemClick(position: Int) {
        val novidade = novidadesList.get(position)

        //Abrindo o fragment AjudaDetailsFragment
        (activity as AjudaActivity).supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_ajudaDetails, AjudaDetailsFragment.newInstance())
            commit()
        }

    }

}