package com.example.filmapp.Home.FragRecyclers

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Classes.Ajuda
import com.example.filmapp.Classes.Media
import com.example.filmapp.Home.Adapters.RecyclerViews.AjudaAdapter
import com.example.filmapp.Home.Adapters.RecyclerViews.DescubraListsAdapter
import com.example.filmapp.Home.AjudaActivity
import com.example.filmapp.Home.fragments.AjudaDetailsFragment
import com.example.filmapp.R
import com.example.filmapp.Series.Ui.SerieSelectedActivity
import kotlinx.android.synthetic.main.activity_ajuda.*
import kotlinx.android.synthetic.main.fragrecycler_duvidaslist.view.*
import kotlinx.android.synthetic.main.fragrecycler_filmesdescubra.view.*

class FragRecycler_filmesDescubra : Fragment(), DescubraListsAdapter.onDescubraItemClickListener {
    private val filmesList = getFilmesList()
    private val filmesList_adapter = DescubraListsAdapter(filmesList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragrecycler_filmesdescubra, container, false)

        //Iniciando o ReciclerView Dúvidas Frequentes
        view.rv_filmesDescubra.adapter = filmesList_adapter
        view.rv_filmesDescubra.layoutManager = LinearLayoutManager(context)
        view.rv_filmesDescubra.isHorizontalFadingEdgeEnabled
        view.rv_filmesDescubra.setHasFixedSize(true)

        return view
    }

    companion object{
        fun newInstance() = FragRecycler_filmesDescubra()
    }

    fun getFilmesList(): ArrayList<Media>{
        return arrayListOf<Media>(
            Media(1,R.drawable.fear_image01,"The Fear Walking Dead", "Filme", "", "Quando: 08/08/12", "Onde:Cinemas", "02h 55min", "Policial, Drama"),
            Media(1,R.drawable.the_boys_image01,"The Boys", "Filme", "", "Quando: 21/08/18", "Onde: Amazon", "02h 55min", "Policial, Drama"),
        )
    }

    override fun descubraItemClick(position: Int) {
        val filme = filmesList.get(position)

        val intent = Intent(context, SerieSelectedActivity::class.java)
        startActivity(intent)
    }

//    override fun assistirMaisTardeIndicationClick(position: Int) {
//        Toast.makeText(context, "Clicou no Assistir Mais Tarde", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun evaluationIndicationClick(position: Int) {
//        Toast.makeText(context, "Clicou na Avaliação", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun shareIndicationIndicationClick(position: Int) {
//        Toast.makeText(context, "Clicou em Compartilhar", Toast.LENGTH_SHORT).show()
//    }

}