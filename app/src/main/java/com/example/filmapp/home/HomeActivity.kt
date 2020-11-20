package com.example.filmapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.filmapp.R
import com.example.filmapp.home.fragments.HomeFragment
import com.example.filmapp.home.fragments.adapters.ViewPagerHomeAdapter
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
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    //Usado para definir as tabs da Home (Home, Séries e Filmes)
    private fun setTabs(){
        val adapter = ViewPagerHomeAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(), "Home")

        viewPager_HomePage.adapter = adapter
        tabLayout_HomePage.setupWithViewPager(viewPager_HomePage)

        //Definição dos ícones de cada tab
        tabLayout_HomePage.getTabAt(0)!!.setIcon(R.drawable.ic_home_roxo)
    }

}