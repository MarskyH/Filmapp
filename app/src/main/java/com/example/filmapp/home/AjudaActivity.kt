package com.example.filmapp.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Classes.Ajuda
import com.example.filmapp.Configuracaoes.ConfiguracoesActivity
import com.example.filmapp.Home.Adapters.RecyclerViews.AjudaAdapter
import com.example.filmapp.Home.fragments.AjudaDetailsFragment
import com.example.filmapp.R
import kotlinx.android.synthetic.main.activity_ajuda.*

class AjudaActivity : AppCompatActivity(), AjudaAdapter.onAjudaItemClickListener{
    private val duvidasList = getDuvidasList()
    private val duvidasList_adapter = AjudaAdapter(duvidasList, this)

    private val novidadesList = getNovidadesList()
    private val novidadesList_adapter = AjudaAdapter(novidadesList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajuda)

        //Iniciando o ReciclerView Novidades
        rc_novidades_ajudaPage.adapter = novidadesList_adapter
        rc_novidades_ajudaPage.layoutManager = LinearLayoutManager(this)
        rc_novidades_ajudaPage.isHorizontalFadingEdgeEnabled
        rc_novidades_ajudaPage.setHasFixedSize(true)

        //Iniciando o ReciclerView Dúvidas Frequentes
        rc_duvidasFrequentes_ajudaPage.adapter = duvidasList_adapter
        rc_duvidasFrequentes_ajudaPage.layoutManager = LinearLayoutManager(this)
        rc_duvidasFrequentes_ajudaPage.isHorizontalFadingEdgeEnabled
        rc_duvidasFrequentes_ajudaPage.setHasFixedSize(true)

        setSupportActionBar(toolbarAjudaPage)

        toolbarAjudaPage.setNavigationOnClickListener {
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

    fun getNovidadesList(): ArrayList<Ajuda>{
        return arrayListOf<Ajuda>(Ajuda(0, "- O que é o Filmapp?", "FilmApp é um aplicativo voltado ao entretenimento com foco em filmes e séries, funcionando como um guia"),
            Ajuda(1, "- O que é o que é?", "Feito para andar e não anda. Resposta: A rua!"),
            Ajuda(2, "- Onde está Wally?", "Where's Wally? é uma série de livros de caráter infanto-juvenil criada pelo ilustrador britânico Martin Handford, baseada em ilustrações e pequenos textos, a série deu origem a uma série animada, uma tira de jornal, uma coleção de 52 revistas semanais intitulada O Mundo de Wally, e jogos eletrônicos."),
            Ajuda(3, "- Onde está Wally?", "Where's Wally? é uma série de livros de caráter infanto-juvenil criada pelo ilustrador britânico Martin Handford, baseada em ilustrações e pequenos textos, a série deu origem a uma série animada, uma tira de jornal, uma coleção de 52 revistas semanais intitulada O Mundo de Wally, e jogos eletrônicos."),
            Ajuda(4, "- Onde está Wally?", "é o ultimo"))
    }

    fun getDuvidasList(): ArrayList<Ajuda>{
        return arrayListOf<Ajuda>(Ajuda(0, "- O que é o Filmapp?", "FilmApp é um aplicativo voltado ao entretenimento com foco em filmes e séries, funcionando como um guia"),
                                    Ajuda(1, "- O que é o que é?", "Feito para andar e não anda. Resposta: A rua!"),
                                    Ajuda(2, "- Onde está Wally?", "Where's Wally? é uma série de livros de caráter infanto-juvenil criada pelo ilustrador britânico Martin Handford, baseada em ilustrações e pequenos textos, a série deu origem a uma série animada, uma tira de jornal, uma coleção de 52 revistas semanais intitulada O Mundo de Wally, e jogos eletrônicos."),
                                    Ajuda(4, "- Onde está Wally?", "é o ultimo"),
                                    Ajuda(0, "- O que é o Filmapp?", "FilmApp é um aplicativo voltado ao entretenimento com foco em filmes e séries, funcionando como um guia"),
                                    Ajuda(1, "- O que é o que é?", "Feito para andar e não anda. Resposta: A rua!"),
                                    Ajuda(2, "- Onde está Wally?", "Where's Wally? é uma série de livros de caráter infanto-juvenil criada pelo ilustrador britânico Martin Handford, baseada em ilustrações e pequenos textos, a série deu origem a uma série animada, uma tira de jornal, uma coleção de 52 revistas semanais intitulada O Mundo de Wally, e jogos eletrônicos."),
                                    Ajuda(3, "- Onde está Wally?", "Where's Wally? é uma série de livros de caráter infanto-juvenil criada pelo ilustrador britânico Martin Handford, baseada em ilustrações e pequenos textos, a série deu origem a uma série animada, uma tira de jornal, uma coleção de 52 revistas semanais intitulada O Mundo de Wally, e jogos eletrônicos."),
                                    Ajuda(4, "- Onde está Wally?", "é o ultimo"))
    }

    override fun ajudaItemClick(position: Int) {
        val duvida = novidadesList.get(position)

//        //Abrindo o fragment AjudaDetailsFragment
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fl_ajudaDetails, AjudaDetailsFragment.newInstance())
//            commit()
//        }

    }

    fun callDescubraPage(){
        val intent = Intent(this, DescubraActivity::class.java)
        startActivity(intent)
    }

    fun callConfiguracoesPage(){
        val intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
    }

}