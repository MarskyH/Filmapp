package com.example.filmapp.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.filmapp.Configuracaoes.ConfiguracoesActivity
import com.example.filmapp.R
import com.example.filmapp.Home.fragments.MelhoresFilmesFragment
import com.example.filmapp.Home.fragments.MelhoresSeriesFragment
import com.example.filmapp.Home.Adapters.ViewPagers.ViewPagerMelhoresAdapter
import kotlinx.android.synthetic.main.activity_melhores.*

class MelhoresActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_melhores)

        setSupportActionBar(toolbarMelhoresPage)

        toolbarMelhoresPage.setNavigationOnClickListener {
            callHome()
        }

        setTabs()
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

    //Usado para definir as tabs Séries e Filmes
    private fun setTabs(){
        val adapter = ViewPagerMelhoresAdapter(supportFragmentManager)
        adapter.addFragment(MelhoresSeriesFragment(), "Séries")
        adapter.addFragment(MelhoresFilmesFragment(), "Filmes")

        viewPager_MelhoresPage.adapter = adapter
        tabLayout_MelhoresPage.setupWithViewPager(viewPager_MelhoresPage)
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