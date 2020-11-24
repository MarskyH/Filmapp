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
import com.example.filmapp.Home.FragRecyclers.FragRecycler_filmesDescubra
import com.example.filmapp.Home.FragRecyclers.FragRecycler_novidadesList
import com.example.filmapp.Home.FragRecyclers.FragRecycler_seriesDescubra
import com.example.filmapp.R
import kotlinx.android.synthetic.main.activity_agenda.*
import kotlinx.android.synthetic.main.activity_descubra.*

class DescubraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descubra)

        //Inflando o RecyclerView de Resultados - Filmes (fragRecycler_filmesDescubra)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragRecycler_filmesDescubraSpace, FragRecycler_filmesDescubra.newInstance())
            commit()
        }

        //Inflando o RecyclerView de Resultados - Filmes (fragRecycler_filmesDescubra)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragRecycler_seriesDescubraSpace, FragRecycler_seriesDescubra.newInstance())
            commit()
        }

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

    fun callConfiguracoesPage(){
        val intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
    }
}