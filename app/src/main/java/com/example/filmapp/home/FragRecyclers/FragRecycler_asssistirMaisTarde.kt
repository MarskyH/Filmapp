package com.example.filmapp.Home.FragRecyclers

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Classes.Media
import com.example.filmapp.Home.Adapters.RecyclerViews.*
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import kotlinx.android.synthetic.main.activity_ajuda.*
import kotlinx.android.synthetic.main.fragrecycler_assistirmaistarde.view.*
import kotlinx.android.synthetic.main.fragrecycler_duvidaslist.view.*
import kotlinx.android.synthetic.main.fragrecycler_emcartaz.view.*
import kotlinx.android.synthetic.main.fragrecycler_filmesdescubra.view.*
import kotlinx.android.synthetic.main.fragrecycler_melhoresdasemana.view.*

class FragRecycler_asssistirMaisTarde : Fragment(), AssistirMaisTardeAdapter.onAssistirMaisTardeItemClickListener {
    private val mediaList = getMediaList()
    private val assistirMaisTarde_adapter = AssistirMaisTardeAdapter(mediaList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragrecycler_assistirmaistarde, container, false)

        //Iniciando o ReciclerView Assistir Mais Tarde
        view.rv_assistirMaisTarde.adapter = assistirMaisTarde_adapter
        view.rv_assistirMaisTarde.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.rv_assistirMaisTarde.isHorizontalFadingEdgeEnabled
        view.rv_assistirMaisTarde.setHasFixedSize(true)

        return view
    }

    companion object{
        fun newInstance() = FragRecycler_asssistirMaisTarde()
    }

    fun getMediaList(): ArrayList<Media>{
        return arrayListOf<Media>(
            Media(1,R.drawable.academy_image01,"The Umbrella Academy", "Série", "12", "21/08/12", "Netflix", "4 Temporadas", "37 Episodeos"),
            Media(1,R.drawable.fear_image01,"The Fear Walking Dead", "Filme", "8", "08/08/12", "Amazon", "8 Temporadas", "2 Episodeos"),
            Media(1,R.drawable.flash_image01,"The Flash", "Série", "85", "21/09/12", "Netflix", "3 Temporadas", "7 Episodeos"),
            Media(1,R.drawable.the_boys_image01,"The Boys", "Filme", "54", "21/08/18", "Amazon", "7 Temporadas", "87 Episodeos"),
            Media(1,R.drawable.grey_image01,"Grey's Anatomy", "Série", "78", "75/08/12", "Netflix", "1 Temporadas", "10 Episodeos"))
    }

    override fun assistirMaisTardeItemClick(position: Int) {
        val media = mediaList.get(position)

        val intent = Intent(context, MediaSelectedActivity::class.java)
        startActivity(intent)
    }

}