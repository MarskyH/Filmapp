package com.example.filmapp.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.filmapp.Classes.Media
import com.example.filmapp.Configuracaoes.ConfiguracoesActivity
import com.example.filmapp.R
import com.example.filmapp.Home.fragments.HomeFragment
import com.example.filmapp.Home.Adapters.ViewPagers.ViewPagerHomeAdapter
import com.example.filmapp.Media.Fragments.HomeMediaFragment
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private var ListFilmes = ArrayList<Media>()
    private var ListSeries = ArrayList<Media>()

    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(service) as T
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbarHomePage)

        setTabs()

//        viewModel.listRes.observe ( this ) {
//            Log.i("Tag HOME", it.toString())
//        }
//
//        viewModel.getAllResults()
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

    //Usado para definir as tabs da Home (Home, Séries e Filmes)
    private fun setTabs(){
        setListFilmes()
        setListSeries()

        val adapter = ViewPagerHomeAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(), "Home")
        adapter.addFragment(HomeMediaFragment(ListSeries, false),"Séries" )
        adapter.addFragment(HomeMediaFragment(ListFilmes, true), "Filmes")

        viewPager_HomePage.adapter = adapter
        tabLayout_HomePage.setupWithViewPager(viewPager_HomePage)

        //Definição dos ícones de cada tab
        tabLayout_HomePage.getTabAt(0)!!.setIcon(R.drawable.ic_home_roxo)
        tabLayout_HomePage.getTabAt(1)!!.setIcon(R.drawable.ic_series_roxo)
        tabLayout_HomePage.getTabAt(2)!!.setIcon(R.drawable.ic_claquete_flaticon)
    }

    fun callDescubraPage(){
        val intent = Intent(this, DescubraActivity::class.java)
        startActivity(intent)
    }

    fun callConfiguracoesPage(){
        val intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
    }
    fun setListSeries(){
        ListSeries = arrayListOf(
            Media(1,
                R.drawable.academy_image01,"The Umbrella Academy", "Série", "2x08 - O Que Eu Sei", "21/08/12", "Netflix", "4 Temporadas", "Sinopse de The Umbrella Academy"),
            Media(1,
                R.drawable.fear_image01,"The Fear Walking Dead", "Filme", "", "08/08/12", "Amazon", "8 Temporadas", "Sinopse de Fear The Walking Dead"),
            Media(1,
                R.drawable.flash_image01,"The Flash", "Série", "2x08 - O Que Eu Sei", "21/09/12", "Netflix", "3 Temporadas", "Sinopse do Flash"),
            Media(1,
                R.drawable.the_boys_image01,"The Boys", "Filme", "", "21/08/18", "Amazon", "7 Temporadas", "Sinopse de The boys"),
            Media(1,
                R.drawable.grey_image01,"Grey's Anatomy", "Série", "2x08 - O Que Eu Sei", "75/08/12", "Netflix", "1 Temporadas", "Sinopse de Grey Anatomy")
        )
    }
    fun setListFilmes(){
        ListFilmes = arrayListOf(
            Media(1,R.drawable.fim,"2012", "Série", "2x08 - O Que Eu Sei", "21/08/12", "Netflix", "4 Temporadas", "Sinopse de 2012"),
            Media(1,R.drawable.star_born,"Nasce um Estrela", "Filme", "", "08/08/12", "Amazon", "8 Temporadas", "Sinopse de Star"),
            Media(1,R.drawable.star_wars,"Star Wars: A Ascensão Skywalker", "Série", "2x08 - O Que Eu Sei", "21/09/12", "Netflix", "3 Temporadas", "Sinopse de Star wars"),
            Media(1,R.drawable.regresso,"O Regresso", "Filme", "", "21/08/18", "Amazon", "7 Temporadas", "Sinopse de O Regresso"),
            Media(1,R.drawable.animais,"Animais Fantásticos e os Crimes de Grindelwald", "Série", "2x08 - O Que Eu Sei", "75/08/12", "Netflix", "1 Temporadas", "Sinopse de Animais Fantásticos "),
            Media(1,R.drawable.ultimato,"Vingadores: Ultimato", "Série", "2x08 - O Que Eu Sei", "75/08/12", "Netflix", "1 Temporadas", "Sinopse de Vingadores Ultimato")
        )
    }

}