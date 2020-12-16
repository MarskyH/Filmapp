package com.example.filmapp.home.descubra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.Media.Fragments.HomeMediaFragment
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.HomeFragment
import com.example.filmapp.home.ViewPagerHomeAdapter
import kotlinx.android.synthetic.main.activity_descubra.*
import kotlinx.android.synthetic.main.activity_home.*

class DescubraActivity : AppCompatActivity() {

     var Text: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descubra)

        val search = findViewById<EditText>(R.id.bar_pesquisa)

        search.setOnEditorActionListener { _, keyCode, event ->
            if (((event?.action ?: -1) == KeyEvent.ACTION_DOWN)
                || keyCode == EditorInfo.IME_ACTION_SEARCH) {
                Text = search.text.toString()
                Log.i("Enter Text", Text)
                DescubraFragment.newInstance(true, Text)
                DescubraFragment.newInstance(false, Text)
                search.text.clear()
                setTabs(Text)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

    }

    private fun setTabs(Text: String) {
        Log.i("Tab TExt", Text)
        val adapter = ViewPagerHomeAdapter(supportFragmentManager)
        val descubraFilme = DescubraFragment.newInstance(true, Text)
        val descubraSerie = DescubraFragment.newInstance(false, Text)
        adapter.addFragment(descubraFilme, "Filmes")
        adapter.addFragment(descubraSerie, "Série")
        viewPagerDescubra.adapter = adapter
        tabsDescubra.setupWithViewPager(viewPagerDescubra)


    }



    //Usado para add o Menu a Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_config, menu)
        return true
    }

    //Usado pra add ações de click aos itens do Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.configurações_toolbarConfig -> {
                callConfiguracoesPage()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun callConfiguracoesPage() {
        val intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
    }

}