package com.example.filmapp.home.descubra

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
import com.example.filmapp.R
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.main.activity_descubra.*

class DescubraActivity : AppCompatActivity(), DescubraListsAdapter.onDescubraItemClickListener {

    private lateinit var mediaListAdapter: DescubraListsAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager

    val viewModel by viewModels<DescubraViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DescubraViewModel(service) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descubra)

        //Iniciando o ReciclerView SearchList
        mediaListLayoutManager = LinearLayoutManager(this)
        mediaListAdapter = DescubraListsAdapter(this)
        rv_SearchList.layoutManager = mediaListLayoutManager
        rv_SearchList.adapter = mediaListAdapter
        rv_SearchList.isHorizontalFadingEdgeEnabled
        rv_SearchList.setHasFixedSize(true)

        viewModel.returnSearchListAPI.observe(this){
            mediaListAdapter.addList(it)

        }

        var name = "batman"
        viewModel.getSearchList(name)

//        //Inflando o RecyclerView de Resultados - Filmes (fragRecycler_filmesDescubra)
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragRecycler_filmesDescubraSpace, FragRecycler_filmesDescubra.newInstance())
//            commit()
//        }
//
//        //Inflando o RecyclerView de Resultados - Filmes (fragRecycler_filmesDescubra)
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragRecycler_seriesDescubraSpace, FragRecycler_seriesDescubra.newInstance())
//            commit()
//        }

        setSupportActionBar(toolbarDescubraPage)

        toolbarDescubraPage.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    //Usado para add o Menu a Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_config, menu)
        return true
    }

    //Usado pra add ações de click aos itens do Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.configurações_toolbarConfig -> {
                callConfiguracoesPage()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun callConfiguracoesPage(){
        val intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
    }

    override fun descubraItemClick(position: Int) {
        viewModel.returnSearchListAPI.observe(this) {
            var media = it.get(position)

        }
    }
}