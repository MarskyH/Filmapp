package com.example.filmapp.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Classes.Media
import com.example.filmapp.Configuracaoes.ConfiguracoesActivity
import com.example.filmapp.Home.Adapters.RecyclerViews.DescubraListsAdapter
import com.example.filmapp.Home.Adapters.RecyclerViews.ProximosAdapter
import com.example.filmapp.R
import kotlinx.android.synthetic.main.activity_agenda.*
import kotlinx.android.synthetic.main.activity_descubra.*

class DescubraActivity : AppCompatActivity() {
    private val seriesList = getSeriesList()
    private val filmesList = getFilmesList()

    private val seriesList_adapter = DescubraListsAdapter(seriesList)
    private val filmesList_adapter = DescubraListsAdapter(filmesList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descubra)

        //Iniciando o ReciclerView Séries
        rv_series_descubra.adapter = seriesList_adapter
        rv_series_descubra.layoutManager = LinearLayoutManager(this)
        rv_series_descubra.isHorizontalFadingEdgeEnabled
        rv_series_descubra.setHasFixedSize(true)

        //Iniciando o ReciclerView Filmes
        rv_filmes_descubra.adapter = filmesList_adapter
        rv_filmes_descubra.layoutManager = LinearLayoutManager(this)
        rv_filmes_descubra.isHorizontalFadingEdgeEnabled
        rv_filmes_descubra.setHasFixedSize(true)

        setSupportActionBar(toolbarDescubraPage)

        toolbarDescubraPage.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    //Usado para add o Menu a Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_config, menu)
        return true
    }

    //Usado pra add ações de click aos itens do Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.configurações_toolbarConfig -> {
                callConfiguracoesPage()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getSeriesList(): ArrayList<Media>{
        return arrayListOf<Media>(
            Media(1,R.drawable.academy_image01,"The Umbrella Academy", "Série", "2x08 - O Que Eu Sei", "Quando: 21/08/12", "Onde: Netflix", "4 Temporadas", "37 Episodeos"),
            Media(1,R.drawable.flash_image01,"The Flash", "Série", "2x08 - O Que Eu Sei", "Quando: 21/09/12", "Onde: Netflix", "3 Temporadas", "7 Episodeos"),
            Media(1,R.drawable.grey_image01,"Grey's Anatomy", "Série", "2x08 - O Que Eu Sei", "Quando: 75/08/12", "Onde: Netflix", "1 Temporadas", "10 Episodeos")
        )
    }

    fun getFilmesList(): ArrayList<Media>{
        return arrayListOf<Media>(
            Media(1,R.drawable.fear_image01,"The Fear Walking Dead", "Filme", "", "Quando: 08/08/12", "Onde:Cinemas", "02h 55min", "Policial, Drama"),
            Media(1,R.drawable.the_boys_image01,"The Boys", "Filme", "", "Quando: 21/08/18", "Onde: Amazon", "02h 55min", "Policial, Drama"),
        )
    }

    fun callConfiguracoesPage(){
        val intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
    }
}