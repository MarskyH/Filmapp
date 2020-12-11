package com.example.filmapp.home.emAlta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.R
import com.example.filmapp.home.descubra.DescubraActivity
import com.example.filmapp.home.HomeActivity
import kotlinx.android.synthetic.main.activity_alta.*

class AltaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alta)

        //Inflando o RecyclerView de Melhores da Semana (FragRecycler_melhoresDaSemana)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragRecycler_melhoresDaSemanaSpace, FragRecycler_melhoresDaSemana.newInstance())
            commit()
        }

        //Inflando o RecyclerView de Em Cartaz (FragRecycler_emCartaz)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragRecycler_emCartazSpace, FragRecycler_emCartaz.newInstance())
            commit()
        }

        //Inflando o RecyclerView de Novos Episódios (FragRecycler_novosEpisodios)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragRecycler_novosEpisodiosSpace, FragRecycler_novosEpisodios.newInstance())
            commit()
        }

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

    fun callDescubraPage(){
        val intent = Intent(this, DescubraActivity::class.java)
        startActivity(intent)
    }

    fun callConfiguracoesPage(){
        val intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
    }
}