package com.example.filmapp.Home.FragRecyclers

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Classes.Media
import com.example.filmapp.Home.Adapters.RecyclerViews.EmCartazAdapter
import com.example.filmapp.R
import com.example.filmapp.Series.Ui.SerieSelectedActivity
import kotlinx.android.synthetic.main.fragrecycler_emcartaz.view.*

class FragRecycler_emCartaz : Fragment(), EmCartazAdapter.onEmCartazItemClickListener {
    private val mediaList = getMediaList()
    private val emCartaz_adapter = EmCartazAdapter(mediaList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragrecycler_emcartaz, container, false)

        //Iniciando o ReciclerView Em Cartaz
        view.rv_emCartaz_EmAlta.adapter = emCartaz_adapter
        view.rv_emCartaz_EmAlta.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.rv_emCartaz_EmAlta.isHorizontalFadingEdgeEnabled
        view.rv_emCartaz_EmAlta.setHasFixedSize(true)

        return view
    }

    companion object{
        fun newInstance() = FragRecycler_emCartaz()
    }

    fun getMediaList(): ArrayList<Media>{
        return arrayListOf<Media>(Media(1,R.drawable.academy_image01,"The Umbrella Academy", "Série", "12", "21/08/12", "Netflix", "4 Temporadas", "37 Episodeos"),
            Media(1,R.drawable.fear_image01,"The Fear Walking Dead", "Filme", "8", "08/08/12", "Amazon", "8 Temporadas", "2 Episodeos"),
            Media(1,R.drawable.flash_image01,"The Flash", "Série", "85", "21/09/12", "Netflix", "3 Temporadas", "7 Episodeos"),
            Media(1,R.drawable.the_boys_image01,"The Boys", "Filme", "54", "21/08/18", "Amazon", "7 Temporadas", "87 Episodeos"),
            Media(1,R.drawable.grey_image01,"Grey's Anatomy", "Série", "78", "75/08/12", "Netflix", "1 Temporadas", "10 Episodeos"))
    }

    override fun emCartazItemClick(position: Int) {
        val media = mediaList.get(position)

        val intent = Intent(context, SerieSelectedActivity::class.java)
        startActivity(intent)
    }

}