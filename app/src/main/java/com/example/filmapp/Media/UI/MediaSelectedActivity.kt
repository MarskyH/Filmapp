package com.example.filmapp.Media.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.Media.Adapters.ViewPagerMedia
import com.example.filmapp.Media.Fragments.GeralMediaFragment
import com.example.filmapp.Media.Fragments.MediaEspecificoFragment
import com.example.filmapp.Media.dataBase.FavoritoScope
import com.example.filmapp.R
import com.example.filmapp.home.descubra.DescubraActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

import kotlinx.android.synthetic.main.activity_media_selected.*
import kotlinx.android.synthetic.main.custom_alert.view.*


class MediaSelectedActivity(): AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_selected)
        setSupportActionBar(toolbarMediasSelected)

        toolbarMediasSelected.setNavigationOnClickListener {
            finish()
        }
        try{
            setUpTabs()
        }catch (e: Exception){
            creatAlert(e)
        }

    }

    private fun setUpTabs() {
        val movie = intent.getSerializableExtra("movie") as? Boolean
        val poster = intent.getSerializableExtra("poster") as? String
        val idMedia = intent.getSerializableExtra("id") as? Int
        val sinopseMedia = intent.getSerializableExtra("sinopse") as? String
        val adapter = ViewPagerMedia(supportFragmentManager)
        if (movie == true) {
            val GeralFilme = GeralMediaFragment.newInstance(sinopseMedia, poster, idMedia.toString(), "Movie")
            val MediaFilme = MediaEspecificoFragment.newInstance(true, idMedia.toString())
            adapter.addFragment(GeralFilme, "Visão Geral")
            adapter.addFragment(MediaFilme, "Semelhantes")
            viewPagerMedias.adapter = adapter
            tabsMedias.setupWithViewPager(viewPagerMedias)
        } else {
            val GeralSerie = GeralMediaFragment.newInstance(sinopseMedia, poster, idMedia.toString(), "Tv")
            val MediaSerie = MediaEspecificoFragment.newInstance(false, idMedia.toString())
            adapter.addFragment(GeralSerie, "Visão Geral")
            adapter.addFragment(MediaSerie, "Temporadas")
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

    fun creatAlert(e : Exception) {
        val user = FirebaseAuth.getInstance().currentUser
        val builder = AlertDialog.Builder(this).create()
        val view: View = LayoutInflater.from(this).inflate(R.layout.custom_alert_erro, null)
        builder.setView(view)
        builder.show()
        view.btAlert_confirm.setOnClickListener {
            val firebaseDB = FirebaseDatabase.getInstance().getReference().child("erros/${user?.uid}").setValue(e)
            Toast.makeText(this, "Erro reportado, desculpe-nos pelo transtorno", Toast.LENGTH_SHORT).show()
            builder.dismiss()
            finish()
        }
        view.btAlert_Notconfirm.setOnClickListener {
            Toast.makeText(this, "Erro ignorado", Toast.LENGTH_SHORT).show()
            builder.dismiss()
            finish()

        }

    }

}







