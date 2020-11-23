package com.example.filmapp.Series.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filmapp.R
import com.example.filmapp.Series.Adapter.ViewPagerSerieAdapter
import com.example.filmapp.Series.Fragments.*
import kotlinx.android.synthetic.main.activity_serie_selected.viewPagerSeries
import kotlinx.android.synthetic.main.activity_serie_temporada_selected.*
import kotlinx.android.synthetic.main.fragment_series_temporada.*

open class SerieTemporadaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie_temporada_selected)
        toolbarTemporada.setNavigationOnClickListener {
            finish()
        }
        setUpTabs()
    }

    fun setUpTabs() {
        val bundle = intent.extras
        val adapter = ViewPagerSerieAdapter(supportFragmentManager)
        if (bundle != null) {
            adapter.addFragment(TemporadaFragment(), "The Boys - ${bundle.getString("Temporada")}")
        }
        adapter.addFragment(EpisodiosFragment(), "Episódios")
        viewPagerSeriesTemporada.adapter = adapter
        tabsSeriesTemporada.setupWithViewPager(viewPagerSeriesTemporada)
    }


}





