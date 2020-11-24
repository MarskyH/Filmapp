package com.example.filmapp.Filmes.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filmapp.R


class SimilarFragment : Fragment(){//, FilmesAdapter.OnSerieClickListener {
//    var listaFilmes = getAllSemelhantes()
//    var adapter = FilmesAdapter(listaFilmes, this)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        rv_semelhantes.adapter = adapter
//        rv_semelhantes.setHasFixedSize(true)
        val view: View = inflater!!.inflate(R.layout.fragment_filmes_semelhantes, container, false)
        return  view

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
            arguments?.getInt("key")?.let {

        }
    }

    companion object{
        @JvmStatic
        fun newInstance(msg: Int) = SimilarFragment().apply {

            }
        }


//    fun getAllSemelhantes(): ArrayList<Filme>{
//        val filme1 = Filme(1, "No Country For Old Men", "1", R.drawable.no_country_for_old_men_image01.png)
//        val filme2 = Filme(2, "The Boys", "1", R.drawable.the_boys_image03)
//        return arrayListOf(filme1, filme2)
//    }

//    override fun filmeClick(position: Int) {
//        val serie = listaFilmes.get(position)
//        serie.temporada = "DEU CERTO"
//        adapter.notifyItemChanged(position)
//    }
}