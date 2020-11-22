package com.example.filmapp.Series.Ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filmapp.R
import com.example.filmapp.Series.Adapter.ViewPagerSerieAdapter
import com.example.filmapp.Series.Fragments.EpisodioFragment
import com.example.filmapp.Series.Fragments.TemporadaFragment

import kotlinx.android.synthetic.main.activity_serie_episodio_selected.*


class SerieEpisodioSelectedActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie_episodio_selected)
        setUpTabs()

        toolbarEpisodioSelected.setNavigationOnClickListener {
            finish()
        }
    }


    private fun setUpTabs() {
        val bundle = intent.extras
        val adapter = ViewPagerSerieAdapter(supportFragmentManager)
        if (bundle != null) {
            adapter.addFragment(EpisodioFragment(), "Temporada 2 - Episódio ${bundle.getInt("Episodio")}")
        }

        viewPagerSeriesEpisodio.adapter = adapter
        tabsSeriesEpisodioSelected.setupWithViewPager(viewPagerSeriesEpisodio)
    }

}

