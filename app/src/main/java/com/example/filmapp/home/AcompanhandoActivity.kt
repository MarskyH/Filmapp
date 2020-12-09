package com.example.filmapp.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.Home.HomeActivity
import com.example.filmapp.home.adapters.RecyclerViews.AcompanhandoAdapter
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.activitys.viewmodels.AcompanhandoViewModel
import kotlinx.android.synthetic.main.activity_acompanhando.*

class AcompanhandoActivity : AppCompatActivity(), AcompanhandoAdapter.onAcompanhandoItemClickListener {

    private lateinit var mediaListAdapter: AcompanhandoAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager

    val viewModel by viewModels<AcompanhandoViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AcompanhandoViewModel(service) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acompanhando)

        //Iniciando o ReciclerView Acompanhando
        mediaListLayoutManager = LinearLayoutManager(this)
        mediaListAdapter = AcompanhandoAdapter(this)
        rv_acompanhandoList.layoutManager = mediaListLayoutManager
        rv_acompanhandoList.adapter = mediaListAdapter
        rv_acompanhandoList.isHorizontalFadingEdgeEnabled
        rv_acompanhandoList.setHasFixedSize(true)

//        viewModel.returnUserAcompanhandoListAPI.observe(this){
//            var mediaList = it.results
//            mediaListAdapter.addList(mediaList)
//        }

        setSupportActionBar(toolbarAcompanhandoPage)

        toolbarAcompanhandoPage.setNavigationOnClickListener {
            callHome()
        }
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

    fun callHome(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    fun callDescubraPage(){
        val intent = Intent(this, DescubraActivity::class.java)
        startActivity(intent)
    }

    fun callConfiguracoesPage(){
        val intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
    }

    override fun AcompanhandoItemClick(position: Int) {
//        viewModel.returnUserAcompanhandoListAPI.observe(this){
//            var mediaList = it.results
//            val serie = mediaList.get(position)
//
//            val intent = Intent(this, MediaSelectedActivity::class.java)
//            startActivity(intent)
//        }
    }
}