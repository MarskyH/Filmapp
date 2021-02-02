package com.example.filmapp.home.historico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.HomeActivity
import com.example.filmapp.home.descubra.DescubraActivity
import com.example.filmapp.home.historico.dataBase.HistoricoEntity
import kotlinx.android.synthetic.main.activity_historico.*
import kotlinx.android.synthetic.main.fragrecycler_assistirmaistarde.*

class HistoricoActivity : AppCompatActivity(), HistoricoAdapter.onHistoricoItemClickListener {

    private lateinit var mediaListAdapter: HistoricoAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager
    private lateinit var viewModel: HistoricoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico)

        viewModel = ViewModelProvider(this).get(HistoricoViewModel::class.java)

        //Iniciando o ReciclerView Histórico
        mediaListLayoutManager = LinearLayoutManager(this)
        mediaListAdapter = HistoricoAdapter(this)
        rv_historicoList.layoutManager = mediaListLayoutManager
        rv_historicoList.adapter = mediaListAdapter
        rv_historicoList.isHorizontalFadingEdgeEnabled
        rv_historicoList.setHasFixedSize(true)

        viewModel.mediaList.observe(this){
            var mediaList = viewModel.formattingItem(it)
            pb_historico.setVisibility(View.INVISIBLE)
            mediaListAdapter.addList(mediaList)
        }

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
        var mediaList = mediaListAdapter.mediaList
        var media = mediaList.get(position)

        val intent = Intent(this, MediaSelectedActivity::class.java)
        intent.putExtra("poster", media.poster_path)

        if (media.type == "Movie")
            intent.putExtra("movie", true)
        else
            intent.putExtra("movie", false)

        intent.putExtra("id", media.id)

        startActivity(intent)
    }

}