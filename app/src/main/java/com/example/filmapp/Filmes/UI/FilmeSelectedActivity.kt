package com.example.filmapp.Filmes.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filmapp.Filmes.Fragments.SimilarFragment
import com.example.filmapp.Filmes.Interfaces.ContractFilmeSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Filmes.Adapter.ViewPagerAdapter
import com.example.filmapp.Filmes.Fragments.GeralFragment
import com.example.filmapp.Filmes.Fragments.MideaFragment
import kotlinx.android.synthetic.main.activity_filme_selected.*

class FilmeSelectedActivity : AppCompatActivity(), ContractFilmeSelectedActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filme_selected)

        setUpTabs()

    }


    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(GeralFragment(), "Visão Geral")
        adapter.addFragment(SimilarFragment(), "Semelhantes")
        adapter.addFragment(MideaFragment(), "Mídia")
        viewPagerFilmes.adapter = adapter
        tabsFilmes.setupWithViewPager(viewPagerFilmes)
    }


    override fun callGeralFrag() {
        val fragGeral = GeralFragment.newInstance()
        supportFragmentManager.beginTransaction().apply {
            commit()
        }
    }
}





