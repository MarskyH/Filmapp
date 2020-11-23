package com.example.filmapp.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Classes.Media
import com.example.filmapp.Home.Adapters.RecyclerViews.AcompanhandoAdapter
import com.example.filmapp.Home.Adapters.RecyclerViews.DescubraListsAdapter
import com.example.filmapp.R
import kotlinx.android.synthetic.main.activity_acompanhando.*
import kotlinx.android.synthetic.main.activity_descubra.*

class AcompanhandoActivity : AppCompatActivity() {
    private val mediaList = getMediaList()
    private val adapter = AcompanhandoAdapter(mediaList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acompanhando)

        //Iniciando o ReciclerView Acompanhando
        rv_acompanhandoList.adapter = adapter
        rv_acompanhandoList.layoutManager = LinearLayoutManager(this)
        rv_acompanhandoList.isHorizontalFadingEdgeEnabled
        rv_acompanhandoList.setHasFixedSize(true)

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

    fun getMediaList(): ArrayList<Media>{
        return arrayListOf<Media>(
            Media(1,R.drawable.academy_image01,"The Umbrella Academy", "Série", "2x08 - O Que Eu Sei", "Quando: 21/08/12", "Onde: Netflix", "4 Temporadas", "37 Episodeos"),
            Media(1,R.drawable.flash_image01,"The Flash", "Série", "2x08 - O Que Eu Sei", "Quando: 21/09/12", "Onde: Netflix", "3 Temporadas", "7 Episodeos"),
            Media(1,R.drawable.grey_image01,"Grey's Anatomy", "Série", "2x08 - O Que Eu Sei", "Quando: 75/08/12", "Onde: Netflix", "1 Temporadas", "10 Episodeos")
        )
    }

    fun callDescubraPage(){
        val intent = Intent(this, DescubraActivity::class.java)
        startActivity(intent)
    }

    fun callConfiguracoesPage(){
        Toast.makeText(this, "Chamando ConfiguraçõesPage", Toast.LENGTH_SHORT).show()
    }
}