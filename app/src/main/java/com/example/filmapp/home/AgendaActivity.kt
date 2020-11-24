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
import com.example.filmapp.Home.Adapters.RecyclerViews.AssistirMaisTardeAdapter
import com.example.filmapp.Home.Adapters.RecyclerViews.MelhoresDaSemanaAdapter
import com.example.filmapp.Home.Adapters.RecyclerViews.ProximosAdapter
import com.example.filmapp.Home.FragRecyclers.FragRecycler_asssistirMaisTarde
import com.example.filmapp.Home.FragRecyclers.FragRecycler_melhoresDaSemana
import com.example.filmapp.Home.FragRecyclers.FragRecycler_proximosAgenda
import com.example.filmapp.R
import kotlinx.android.synthetic.main.activity_agenda.*
import kotlinx.android.synthetic.main.activity_alta.*

class AgendaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)

        //Inflando o RecyclerView de Assistir Mais Tarde (FragRecycler_assistirMaisTarde)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragRecycler_assistirMaisTardeSpace, FragRecycler_asssistirMaisTarde.newInstance())
            commit()
        }

        //Inflando o RecyclerView de Próximos (FragRecycler_proximosAgenda)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragRecycler_proximosSpace, FragRecycler_proximosAgenda.newInstance())
            commit()
        }

        setSupportActionBar(toolbarAgendaPage)

        toolbarAgendaPage.setNavigationOnClickListener {
            callHome()
        }

        floatinButton_agendaPage.setOnClickListener {
            callDescubraPage()
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

    fun callDescubraPage(){
        val intent = Intent(this, DescubraActivity::class.java)
        startActivity(intent)
    }

    fun callConfiguracoesPage(){
        val intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
    }
}