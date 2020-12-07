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
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.R
import com.example.filmapp.Home.fragments.HomeFragment
import com.example.filmapp.Home.Adapters.ViewPagers.ViewPagerHomeAdapter
import com.example.filmapp.Media.Fragments.HomeMediaFragment
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private var ListFilmes = ArrayList<ResultMovie>()
    private var ListSeries = ArrayList<ResultTv>()

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

        viewModel.listResMovies.observe(this) {
            ListFilmes = it.results
            Log.i("HomeActivity - filmes",it.toString())
        }

        viewModel.listResSeries.observe(this) {
            ListSeries = it.resultTvs
            Log.i("HomeActivity - series",it.toString())
        }

        viewModel.getPopularSeries()
        viewModel.getPopularMovies()

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
        adapter.addFragment(HomeFragment(), "Home")
        adapter.addFragment(HomeMediaFragment(ListFilmes, ListSeries, false), "Séries")
        adapter.addFragment(HomeMediaFragment(ListFilmes, ListSeries, true), "Filmes")
        Log.i("HomeActivity",ListFilmes.toString())
        Log.i("HomeActivity",ListSeries.toString())

        viewPager_HomePage.adapter = adapter
        tabLayout_HomePage.setupWithViewPager(viewPager_HomePage)

        //Definição dos ícones de cada tab
        tabLayout_HomePage.getTabAt(0)!!.setIcon(R.drawable.ic_home_roxo)
        tabLayout_HomePage.getTabAt(1)!!.setIcon(R.drawable.ic_series_roxo)
        tabLayout_HomePage.getTabAt(2)!!.setIcon(R.drawable.ic_claquete_flaticon)
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