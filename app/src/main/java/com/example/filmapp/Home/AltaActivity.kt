package com.example.filmapp.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Classes.Media
import com.example.filmapp.Home.Adapters.RecyclerViews.EmCartazAdapter
import com.example.filmapp.Home.Adapters.RecyclerViews.MelhoresDaSemanaAdapter
import com.example.filmapp.Home.Adapters.RecyclerViews.NovosEpisodiosAdapter
import com.example.filmapp.R
import kotlinx.android.synthetic.main.activity_alta.*

class AltaActivity : AppCompatActivity() {
    private val mediaList = getMediaList()
    private val melhoresDaSemana_adapter = MelhoresDaSemanaAdapter(mediaList)
    private val emCartaz_adapter = EmCartazAdapter(mediaList)
    private val novosEpisodios_adapter = NovosEpisodiosAdapter(mediaList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alta)

        //Iniciando o ReciclerView Melhores da Semana
        rv_melhoresDaSemana_EmAlta.adapter = melhoresDaSemana_adapter
        rv_melhoresDaSemana_EmAlta.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_melhoresDaSemana_EmAlta.isHorizontalFadingEdgeEnabled
        rv_melhoresDaSemana_EmAlta.setHasFixedSize(true)

        //Iniciando o ReciclerView Em Cartaz
        rv_emCartaz_EmAlta.adapter = emCartaz_adapter
        rv_emCartaz_EmAlta.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_emCartaz_EmAlta.isHorizontalFadingEdgeEnabled
        rv_emCartaz_EmAlta.setHasFixedSize(true)

        //Iniciando o ReciclerView Novos Episódios
        rv_novosEpisodeos_EmAlta.adapter = novosEpisodios_adapter
        rv_novosEpisodeos_EmAlta.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_novosEpisodeos_EmAlta.isHorizontalFadingEdgeEnabled
        rv_novosEpisodeos_EmAlta.setHasFixedSize(true)

        setSupportActionBar(toolbarEmAltaPage)

        toolbarEmAltaPage.setNavigationOnClickListener {
            callHome()
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
        return arrayListOf<Media>(Media(1,R.drawable.academy_image01,"The Umbrella Academy", "Série", "12", "21/08/12", "Netflix", "4 Temporadas", "37 Episodeos"),
                            Media(1,R.drawable.fear_image01,"The Fear Walking Dead", "Filme", "8", "08/08/12", "Amazon", "8 Temporadas", "2 Episodeos"),
                            Media(1,R.drawable.flash_image01,"The Flash", "Série", "85", "21/09/12", "Netflix", "3 Temporadas", "7 Episodeos"),
                            Media(1,R.drawable.the_boys_image01,"The Boys", "Filme", "54", "21/08/18", "Amazon", "7 Temporadas", "87 Episodeos"),
                            Media(1,R.drawable.grey_image01,"Grey's Anatomy", "Série", "78", "75/08/12", "Netflix", "1 Temporadas", "10 Episodeos"))
    }

    fun callDescubraPage(){
        val intent = Intent(this, DescubraActivity::class.java)
        startActivity(intent)
    }

    fun callConfiguracoesPage(){
        Toast.makeText(this, "Chamando ConfiguraçõesPage", Toast.LENGTH_SHORT).show()
    }
}