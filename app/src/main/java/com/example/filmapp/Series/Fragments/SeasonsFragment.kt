package com.example.filmapp.Series.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filmapp.R


class SeasonsFragment : Fragment(){//, SeriesAdapter.OnSerieClickListener {
//    var listaSeries = getAllTemporadas()
//    var adapter = SeriesAdapter(listaSeries, this)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        rv_temporada.adapter = adapter
//        rv_temporada.setHasFixedSize(true)
        val view: View = inflater!!.inflate(R.layout.fragment_series_seasons, container, false)
        return  view

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
            arguments?.getInt("key")?.let {

        }
    }

    companion object{
        @JvmStatic
        fun newInstance(msg: Int) = SeasonsFragment().apply {

            }
        }


//    fun getAllTemporadas(): ArrayList<Serie>{
//        val serie1 = Serie(1, "The Boys", "1", R.drawable.the_boys_image02)
//        val serie2 = Serie(2, "The Boys", "1", R.drawable.the_boys_image03)
//        return arrayListOf(serie1, serie2)
//    }

//    override fun serieClick(position: Int) {
//        val serie = listaSeries.get(position)
//        serie.temporada = "DEU CERTO"
//        adapter.notifyItemChanged(position)
//    }
}