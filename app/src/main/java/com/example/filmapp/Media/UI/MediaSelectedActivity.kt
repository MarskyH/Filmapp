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


class MediaSelectedActivity : AppCompatActivity() {



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
        var movie = intent.getSerializableExtra("movie") as? Boolean
        var poster = intent.getSerializableExtra("poster") as? String
        val adapter = ViewPagerMedia(supportFragmentManager)
        if (movie == true) {
            val mediaSelect = intent.getSerializableExtra("media") as? ResultMovie
            val sinopse = mediaSelect?.overview
            val GeralFilme = GeralMediaFragment.newInstance(sinopse, poster)
            val MediaFilme = MediaEspecificoFragment.newInstance(true, mediaSelect)
            val ResourceFilme = ResourcesFragment.newInstance(true, mediaSelect)
            Log.i("MediaSelectActivity", mediaSelect.toString())
            adapter.addFragment(GeralFilme, "Visão Geral")
            adapter.addFragment(MediaFilme, "Semelhantes")
            adapter.addFragment(ResourceFilme, "Mídia")
            viewPagerMedias.adapter = adapter
            tabsMedias.setupWithViewPager(viewPagerMedias)
        } else {
            val mediaSelect = intent.getSerializableExtra("media") as? ResultTv
            val sinopse = mediaSelect?.overview
            val GeralSerie = GeralMediaFragment.newInstance(sinopse, poster)
            val MediaSerie = MediaEspecificoFragment.newInstance(false, mediaSelect)
            val ResourceSerie = ResourcesFragment.newInstance(false, mediaSelect)
            Log.i("MediaSelect", mediaSelect.toString())
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







