package com.example.filmapp.home.acompanhando

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
import com.example.filmapp.home.descubra.DescubraActivity
import com.example.filmapp.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_acompanhando.*

class AcompanhandoActivity : AppCompatActivity(), AcompanhandoAdapter.onAcompanhandoItemClickListener {
    val user = FirebaseAuth.getInstance().currentUser
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
        Log.i("USER ID", user!!.uid)
        //Iniciando o ReciclerView Acompanhando
        mediaListLayoutManager = LinearLayoutManager(this)
        mediaListAdapter = AcompanhandoAdapter(this)
        rv_acompanhandoList.layoutManager = mediaListLayoutManager
        rv_acompanhandoList.adapter = mediaListAdapter
        rv_acompanhandoList.isHorizontalFadingEdgeEnabled
        rv_acompanhandoList.setHasFixedSize(true)

        viewModel.getAcompanhadoList()

        viewModel.returnAcompanhandoList.observe(this){
            viewModel.getCurrentStatusSeries(it)
        }

        viewModel.listUpdated.observe(this){
            pb_acompanhando.setVisibility(View.INVISIBLE)
            mediaListAdapter.addList(it)
        }

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
        viewModel.listUpdated.observe(this) {
            var mediaList = it
            var media = mediaList.get(position)

            val intent = Intent(this, MediaSelectedActivity::class.java)
            intent.putExtra("poster", "https://image.tmdb.org/t/p/w500" + media.poster_path)
            intent.putExtra("movie", false)
            intent.putExtra("id", media.id)

            startActivity(intent)
        }
    }
}