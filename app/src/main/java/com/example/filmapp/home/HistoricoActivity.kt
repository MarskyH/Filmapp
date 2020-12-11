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
import com.example.filmapp.home.activitys.HistoricoAdapter
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.activitys.viewmodels.HistoricoViewModel
import kotlinx.android.synthetic.main.activity_historico.*

class HistoricoActivity : AppCompatActivity(), HistoricoAdapter.onHistoricoItemClickListener {

    private lateinit var mediaListAdapter: HistoricoAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager

    val viewModel by viewModels<HistoricoViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HistoricoViewModel(service) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico)

        //Iniciando o ReciclerView Histórico
        mediaListLayoutManager = LinearLayoutManager(this)
        mediaListAdapter = HistoricoAdapter(this)
        rv_historicoList.layoutManager = mediaListLayoutManager
        rv_historicoList.adapter = mediaListAdapter
        rv_historicoList.isHorizontalFadingEdgeEnabled
        rv_historicoList.setHasFixedSize(true)

//        viewModel.returnUserHistoricoAPI.observe(this){
//            mediaListAdapter.addList(it)
//        }

        setSupportActionBar(toolbarHistoricoPage)

        toolbarHistoricoPage.setNavigationOnClickListener {
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

    override fun historicoItemClick(position: Int) {
//        val media = mediaList.get(position)

        val intent = Intent(this, MediaSelectedActivity::class.java)
        startActivity(intent)
    }

}