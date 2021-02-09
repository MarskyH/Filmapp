package com.example.filmapp.Series.Ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.Media.Adapters.ViewPagerMedia
import com.example.filmapp.R
import com.example.filmapp.Series.Fragments.EpisodioFragment
import com.example.filmapp.home.descubra.DescubraActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_serie_episodio_selected.*
import kotlinx.android.synthetic.main.custom_alert.view.*
import kotlinx.android.synthetic.main.fragment_series_episodio.*


class SerieEpisodioSelectedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie_episodio_selected)

        setSupportActionBar(toolbarEpisodioSelected)

        try {
            setUpTabs()
        } catch (e: Exception) {
            creatAlertException(e)
        }


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
        val episodeTitle = intent.getSerializableExtra("episodeTitle") as? String
        val sinopseEp = intent.getSerializableExtra("sinopse_episode") as? String
        val img = intent.getSerializableExtra("imagem") as? String
        val imgLogo = intent.getSerializableExtra("logo") as? String
        val homepage = intent.getSerializableExtra("homepage") as? String
        val id = intent.getSerializableExtra("id") as? String
        val id_ep = intent.getSerializableExtra("id_ep") as? String
        val title = intent.getSerializableExtra("title") as? String
        val poster = intent.getSerializableExtra("poster") as? String
        val titulo = "Temporada ${numberSeason} - Episódio ${numberEp}"
        val Episodio = EpisodioFragment.newInstance(
            sinopseEp,
            poster,
            id,
            "Tv",
            img,
            imgLogo,
            homepage!!,
            title,
            id_ep,
            numberEp,
            numberSeason,
            episodeTitle
        )
        val adapter = ViewPagerMedia(supportFragmentManager)
        adapter.addFragment(Episodio, titulo)
        viewPagerSeriesEpisodio.adapter = adapter
        tabsSeriesEpisodioSelected.setupWithViewPager(viewPagerSeriesEpisodio)
    }

    fun creatAlertException(e: Exception) {
        val user = FirebaseAuth.getInstance().currentUser
        val builder = AlertDialog.Builder(this).create()
        val view: View = LayoutInflater.from(this).inflate(R.layout.custom_alert_erro, null)
        builder.setView(view)
        builder.show()
        view.btAlert_confirm.setOnClickListener {
            val firebaseDB =
                FirebaseDatabase.getInstance().getReference().child("erros/${user?.uid}")
                    .setValue(e)
            Toast.makeText(
                this,
                "Erro reportado, desculpe-nos pelo transtorno",
                Toast.LENGTH_SHORT
            ).show()
            builder.dismiss()
            finish();
        }
        view.btAlert_Notconfirm.setOnClickListener {
            Toast.makeText(this, "Erro ignorado", Toast.LENGTH_SHORT).show()
            builder.dismiss()
            finish();

        }

    }

}

