package com.example.filmapp.Media.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Media.Adapters.ViewPagerMedia
import com.example.filmapp.Media.Fragments.GeralMediaFragment
import com.example.filmapp.Media.Fragments.HomeMediaFragment
import com.example.filmapp.Media.Fragments.MediaEspecificoFragment
import com.example.filmapp.Media.Fragments.ResourcesFragment
import com.example.filmapp.R
import com.example.filmapp.home.DescubraActivity

import kotlinx.android.synthetic.main.activity_media_selected.*


class MediaSelectedActivity(): AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_selected)
        setSupportActionBar(toolbarMediasSelected)

        toolbarMediasSelected.setNavigationOnClickListener {
            finish()
        }

        setUpTabs()
    }

    private fun setUpTabs() {
        val movie = intent.getSerializableExtra("movie") as? Boolean
        Log.i("Media movie", movie.toString())
        val poster = intent.getSerializableExtra("poster") as? String
        Log.i("Media poster", poster.toString())
        var mediaFilme = intent.getSerializableExtra("mediaMovie") as? ResultMovie
        if (mediaFilme == null){
            mediaFilme = intent.getSerializableExtra("mediaMovieSimilar") as? ResultMovie
        }
        Log.i("Media Filme", mediaFilme.toString())
        val mediaSerie = intent.getSerializableExtra("mediaSerie") as? ResultTv
        Log.i("Media Serie", mediaSerie.toString())
        val adapter = ViewPagerMedia(supportFragmentManager)
        if (movie == true) {
            val sinopse = mediaFilme?.overview
            val GeralFilme = GeralMediaFragment.newInstance(sinopse, poster)
            val MediaFilme = MediaEspecificoFragment.newInstance(true, mediaFilme)
            val ResourceFilme = ResourcesFragment.newInstance(true, mediaFilme)
            Log.i("MediaSelectFilme", mediaFilme.toString())
            adapter.addFragment(GeralFilme, "Visão Geral")
            adapter.addFragment(MediaFilme, "Semelhantes")
            adapter.addFragment(ResourceFilme, "Mídia")
            viewPagerMedias.adapter = adapter
            tabsMedias.setupWithViewPager(viewPagerMedias)
        } else {
            val sinopse = mediaSerie?.overview
            val GeralSerie = GeralMediaFragment.newInstance(sinopse, poster)
            val MediaSerie = MediaEspecificoFragment.newInstance(false, mediaSerie)
            val ResourceSerie = ResourcesFragment.newInstance(false, mediaSerie)
            Log.i("MediaSelectSerie", mediaSerie.toString())
            adapter.addFragment(GeralSerie, "Visão Geral")
            adapter.addFragment(MediaSerie, "Temporadas")
            adapter.addFragment(ResourceSerie, "Mídia")
            viewPagerMedias.adapter = adapter
            tabsMedias.setupWithViewPager(viewPagerMedias)
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

    fun setObeject(MediaSelect: Any?): Any?{
        return MediaSelect
    }
}







