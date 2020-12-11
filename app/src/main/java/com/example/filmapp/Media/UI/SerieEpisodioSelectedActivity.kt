package com.example.filmapp.Series.Ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.Media.Adapters.ViewPagerMedia
import com.example.filmapp.R
import com.example.filmapp.Series.Fragments.EpisodioFragment
import com.example.filmapp.home.descubra.DescubraActivity
import kotlinx.android.synthetic.main.activity_serie_episodio_selected.*


class SerieEpisodioSelectedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie_episodio_selected)

        setSupportActionBar(toolbarEpisodioSelected)

        setUpTabs()

        toolbarEpisodioSelected.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    //Usado pra add ações de click aos itens do Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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

    fun callDescubraPage() {
        val intent = Intent(this, DescubraActivity::class.java)
        startActivity(intent)
    }

    fun callConfiguracoesPage() {
        val intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
    }

    private fun setUpTabs() {
        val numberSeason = intent.getSerializableExtra("number_season") as? Int
        val numberEp = intent.getSerializableExtra("number_episode") as? Int
        val sinopseEp = intent.getSerializableExtra("sinopse_episode") as? String
        val img = intent.getSerializableExtra("imagem") as? String
        val imgLogo = intent.getSerializableExtra("logo") as? String
        val homepage = intent.getSerializableExtra("homepage") as? String
        val titulo = "Temporada ${numberSeason} - Episódio ${numberEp}"


        val adapter = ViewPagerMedia(supportFragmentManager)
        adapter.addFragment(EpisodioFragment(img, sinopseEp, imgLogo, homepage), titulo)
        viewPagerSeriesEpisodio.adapter = adapter
        tabsSeriesEpisodioSelected.setupWithViewPager(viewPagerSeriesEpisodio)
    }

}

