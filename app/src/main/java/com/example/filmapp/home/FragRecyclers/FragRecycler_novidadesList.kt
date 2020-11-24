package com.example.filmapp.Home.FragRecyclers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Classes.Ajuda
import com.example.filmapp.Home.Adapters.RecyclerViews.AjudaAdapter
import com.example.filmapp.Home.AjudaActivity
import com.example.filmapp.Home.fragments.AjudaDetailsFragment
import com.example.filmapp.R
import kotlinx.android.synthetic.main.activity_ajuda.*
import kotlinx.android.synthetic.main.fragrecycler_duvidaslist.view.*
import kotlinx.android.synthetic.main.fragrecycler_novidadeslist.view.*

class FragRecycler_novidadesList : Fragment(), AjudaAdapter.onAjudaItemClickListener {
    private val novidadesList = getNovidadesList()
    private val novidadesList_adapter = AjudaAdapter(novidadesList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragrecycler_novidadeslist, container, false)

        //Iniciando o ReciclerView Dúvidas Frequentes
        view.rc_novidades.adapter = novidadesList_adapter
        view.rc_novidades.layoutManager = LinearLayoutManager(context)
        view.rc_novidades.isHorizontalFadingEdgeEnabled
        view.rc_novidades.setHasFixedSize(true)

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