package com.example.filmapp.Series.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Filmes.Adapter.FilmesAdapter
import com.example.filmapp.Filmes.Classes.Filme
import com.example.filmapp.R
import kotlinx.android.synthetic.main.fragment_filmes_semelhantes.view.*



class SimilarFragment : Fragment(), FilmesAdapter.OnFilmeClickListener {
    var listaSemelhantes = getAllSemenhantes()
    var adapter = FilmesAdapter(listaSemelhantes, this)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_filmes_semelhantes, container, false)
        view.rv_semelhantes.adapter = adapter
        view.rv_semelhantes.layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)
        view.rv_semelhantes.setHasFixedSize(true)
        return  view

    }

    fun getAllSemenhantes(): ArrayList<Filme>{
      val filme = Filme(1, "Star Wars: A Ascensão Skywalker", R.drawable.star_wars)

        return arrayListOf(filme, filme, filme,filme,filme,filme)
    }


    override fun filmeClick(position: Int) {
        val serie = listaSemelhantes.get(position)
        adapter.notifyItemChanged(position)
    }
}