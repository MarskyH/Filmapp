package com.example.filmapp.Series

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filmapp.R
import kotlinx.android.synthetic.main.activity_serie_selected.*

class SerieSelectedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie_selected)

        setUpTabs()
    }

    private fun setUpTabs(){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(GeralFragment(), "Visão Geral")
        adapter.addFragment(SeasonsFragment(), "Temporadas")
        adapter.addFragment(MideaFragment(), "Mídea")
        viewPagerSeries.adapter = adapter
        tabsSeries.setupWithViewPager(viewPagerSeries)


    }
}