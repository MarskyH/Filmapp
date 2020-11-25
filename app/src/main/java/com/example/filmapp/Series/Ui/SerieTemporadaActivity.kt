package com.example.filmapp.Series.Ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.filmapp.Configuracaoes.ConfiguracoesActivity
import com.example.filmapp.Home.DescubraActivity
import com.example.filmapp.R
import com.example.filmapp.Series.Adapter.ViewPagerSerieAdapter
import com.example.filmapp.Series.Fragments.*
import kotlinx.android.synthetic.main.activity_serie_episodio_selected.*
import kotlinx.android.synthetic.main.activity_serie_selected.viewPagerSeries
import kotlinx.android.synthetic.main.activity_serie_temporada_selected.*
import kotlinx.android.synthetic.main.fragment_series_temporada.*

open class SerieTemporadaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie_temporada_selected)

        setSupportActionBar(toolbarTemporada)

        toolbarTemporada.setNavigationOnClickListener {
            finish()
        }
        setUpTabs()
    }

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

    fun callDescubraPage(){
        val intent = Intent(this, DescubraActivity::class.java)
        startActivity(intent)
    }

    fun callConfiguracoesPage(){
        val intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
    }


    fun setUpTabs() {
        val bundle = intent.extras
        val adapter = ViewPagerSerieAdapter(supportFragmentManager)
        if (bundle != null) {
            adapter.addFragment(TemporadaFragment(), "The Boys - ${bundle.getString("Temporada")}")
        }
        adapter.addFragment(EpisodiosFragment(), "Episódios")
        viewPagerSeriesTemporada.adapter = adapter
        tabsSeriesTemporada.setupWithViewPager(viewPagerSeriesTemporada)
    }


}





