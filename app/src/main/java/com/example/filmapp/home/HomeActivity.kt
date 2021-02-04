package com.example.filmapp.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.R
import com.example.filmapp.Media.Fragments.HomeMediaFragment
import com.example.filmapp.home.descubra.DescubraActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbarHomePage)

        setTabs()
    }

    //Usado para add o Menu a Toolbar
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

    //Usado para definir as tabs da Home (Home, Séries e Filmes)
    private fun setTabs() {

        val adapter = ViewPagerHomeAdapter(supportFragmentManager)
        val homeMediaFilme = HomeMediaFragment.newInstance(true)
        val homeMediaSerie = HomeMediaFragment.newInstance(false)
        adapter.addFragment(homeMediaSerie, "Séries")
        adapter.addFragment(HomeFragment(), "Home")
        adapter.addFragment(homeMediaFilme, "Filmes")

        viewPager_HomePage.adapter = adapter
        tabLayout_HomePage.setupWithViewPager(viewPager_HomePage)


        //Definição dos ícones de cada tab
        tabLayout_HomePage.getTabAt(0)!!.setIcon(R.drawable.ic_series_roxo)
        tabLayout_HomePage.getTabAt(1)!!.setIcon(R.drawable.ic_home_roxo)
        tabLayout_HomePage.getTabAt(2)!!.setIcon(R.drawable.ic_claquete_flaticon)
        viewPager_HomePage.setCurrentItem(1)
    }


    fun callDescubraPage() {
        val intent = Intent(this, DescubraActivity::class.java)
        startActivity(intent)
    }


    fun callConfiguracoesPage() {
        val intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
    }

}
