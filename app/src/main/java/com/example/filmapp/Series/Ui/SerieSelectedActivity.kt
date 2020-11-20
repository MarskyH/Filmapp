package com.example.filmapp.Series.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filmapp.R
import com.example.filmapp.Series.Adapter.ViewPagerAdapter
import com.example.filmapp.Series.Fragments.GeralFragment
import com.example.filmapp.Series.Fragments.MideaFragment
import com.example.filmapp.Series.Fragments.SeasonsFragment
import com.example.filmapp.Series.Interfaces.ContractSerieSelectedActivity
import kotlinx.android.synthetic.main.activity_serie_selected.*

class SerieSelectedActivity : AppCompatActivity(), ContractSerieSelectedActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie_selected)

        setUpTabs()
    }


    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(GeralFragment(), "Visão Geral")
        adapter.addFragment(SeasonsFragment(), "Temporadas")
        adapter.addFragment(MideaFragment(), "Mídea")
        viewPagerSeries.adapter = adapter
        tabsSeries.setupWithViewPager(viewPagerSeries)
    }


    override fun callGeralFrag() {
        val fragGeral = GeralFragment.newInstance()
        supportFragmentManager.beginTransaction().apply {
            commit()
        }
    }
}





