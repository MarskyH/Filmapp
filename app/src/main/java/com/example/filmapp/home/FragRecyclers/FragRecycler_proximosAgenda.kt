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
import kotlinx.android.synthetic.main.fragrecycler_proximosagenda.view.*
class FragRecycler_proximosAgenda : Fragment(), ProximosAdapter.onProximosItemClickListener {
    private val mediaList = getMediaList()
    private val proximos_adapter = ProximosAdapter(mediaList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragrecycler_proximosagenda, container, false)

        //Iniciando o ReciclerView Próximos da AgendaPage
        view.rv_proximos_agenda.adapter = proximos_adapter
        view.rv_proximos_agenda.layoutManager = LinearLayoutManager(context)
        view.rv_proximos_agenda.isHorizontalFadingEdgeEnabled
        view.rv_proximos_agenda.setHasFixedSize(true)

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