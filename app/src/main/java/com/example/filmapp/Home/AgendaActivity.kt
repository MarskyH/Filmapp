package com.example.filmapp.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Classes.Media
import com.example.filmapp.Home.Adapters.RecyclerViews.AssistirMaisTardeAdapter
import com.example.filmapp.Home.Adapters.RecyclerViews.MelhoresDaSemanaAdapter
import com.example.filmapp.Home.Adapters.RecyclerViews.ProximosAdapter
import com.example.filmapp.R
import kotlinx.android.synthetic.main.activity_agenda.*
import kotlinx.android.synthetic.main.activity_alta.*

class AgendaActivity : AppCompatActivity() {
    private val mediaList = getMediaList()
    private val assistirMaisTarde_adapter = AssistirMaisTardeAdapter(mediaList)
    private val proximos_adapter = ProximosAdapter(mediaList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)

        //Iniciando o ReciclerView Assistir Mais Tarde
        rv_assistirMaisTarde_agenda.adapter = assistirMaisTarde_adapter
        rv_assistirMaisTarde_agenda.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_assistirMaisTarde_agenda.isHorizontalFadingEdgeEnabled
        rv_assistirMaisTarde_agenda.setHasFixedSize(true)

        //Iniciando o ReciclerView Próximos
        rv_proximos_agenda.adapter = proximos_adapter
        rv_proximos_agenda.layoutManager = LinearLayoutManager(this)
        rv_proximos_agenda.isHorizontalFadingEdgeEnabled
        rv_proximos_agenda.setHasFixedSize(true)

        setSupportActionBar(toolbarAgendaPage)

        toolbarAgendaPage.setNavigationOnClickListener {
            callHome()
        }

        floatinButton_agendaPage.setOnClickListener {
            callDescubraPage()
        }
    }

    //Usado para add o Menu a Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    //Usado pra add ações de click aos itens do Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.descubra_toolbarMenu -> {
                callDescubraPage()
                true
            }

            R.id.configurações_toolbarMenu -> {
                callConfiguracoesPage()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun callHome(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }


    fun getMediaList(): ArrayList<Media>{
        return arrayListOf<Media>(
            Media(1,R.drawable.academy_image01,"The Umbrella Academy", "Série", "2x08 - O Que Eu Sei", "Quando: 21/08/12", "Onde: Netflix", "4 Temporadas", "37 Episodeos"),
            Media(1,R.drawable.fear_image01,"The Fear Walking Dead", "Filme", "", "Quando: 08/08/12", "Onde:Cinemas", "8 Temporadas", "2 Episodeos"),
            Media(1,R.drawable.flash_image01,"The Flash", "Série", "2x08 - O Que Eu Sei", "Quando: 21/09/12", "Onde: Netflix", "3 Temporadas", "7 Episodeos"),
            Media(1,R.drawable.the_boys_image01,"The Boys", "Filme", "", "Quando: 21/08/18", "Onde: Amazon", "7 Temporadas", "87 Episodeos"),
            Media(1,R.drawable.grey_image01,"Grey's Anatomy", "Série", "2x08 - O Que Eu Sei", "Quando: 75/08/12", "Onde: Netflix", "1 Temporadas", "10 Episodeos")
        )
    }

    fun callDescubraPage(){
        val intent = Intent(this, DescubraActivity::class.java)
        startActivity(intent)
    }

    fun callConfiguracoesPage(){
        Toast.makeText(this, "Chamando ConfiguraçõesPage", Toast.LENGTH_SHORT).show()
    }
}