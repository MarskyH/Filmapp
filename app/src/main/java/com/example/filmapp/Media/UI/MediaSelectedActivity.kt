package com.example.filmapp.Media.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.Home.DescubraActivity
import com.example.filmapp.Media.Fragments.GeralMediaFragment
import com.example.filmapp.Media.Fragments.MediaEspecificoFragment
import com.example.filmapp.Media.Fragments.MediaFragment
import com.example.filmapp.R
import com.example.filmapp.Media.Adapters.ViewPagerSerieMedia
import com.example.filmapp.Media.Models.MediaViewModel
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service

import kotlinx.android.synthetic.main.activity_media_selected.*


class MediaSelectedActivity : AppCompatActivity() {

    private val viewModel by viewModels<MediaViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(service) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_selected)
        setSupportActionBar(toolbarMediasSelected)

        toolbarMediasSelected.setNavigationOnClickListener {
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


    private fun setUpTabs() {
        val bundle = intent.extras
        val adapter = ViewPagerSerieMedia(supportFragmentManager)
        if (bundle != null) {
            val img = bundle.getInt("imagem")
            val sinopse = bundle.getString("sinopse")
            val movie = bundle.getBoolean("movie?")
            adapter.addFragment(GeralMediaFragment(img, sinopse), "Visão Geral")
            if (movie == true){
                adapter.addFragment(MediaEspecificoFragment(movie), "Semelhantes")
            }else{
                adapter.addFragment(MediaEspecificoFragment(movie), "Temporadas")
            }
            adapter.addFragment(MediaFragment(), "Mídia")

            viewPagerMedias.adapter = adapter
            tabsMedias.setupWithViewPager(viewPagerMedias)
        }
    }

}





