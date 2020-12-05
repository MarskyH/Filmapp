package com.example.filmapp.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.Home.FragRecyclers.FragRecycler_duvidasList
import com.example.filmapp.Home.FragRecyclers.FragRecycler_novidadesList
import com.example.filmapp.R
import kotlinx.android.synthetic.main.activity_ajuda.*

class AjudaActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajuda)

        //Inflando o RecyclerView de Novidades (fragRecycler_novidadesList)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragRecycler_novidadesSpace, FragRecycler_novidadesList.newInstance())
            commit()
        }

        //Inflando o RecyclerView de Dúvidas (fragRecycler_duvidasList)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragRecycler_duvidasSpace, FragRecycler_duvidasList.newInstance())
            commit()
        }

        setSupportActionBar(toolbarAjudaPage)

        toolbarAjudaPage.setNavigationOnClickListener {
            onBackPressed()
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