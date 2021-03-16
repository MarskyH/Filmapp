package com.example.filmapp.home.agenda

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.R
import com.example.filmapp.home.descubra.DescubraActivity
import com.example.filmapp.home.HomeActivity
import kotlinx.android.synthetic.main.activity_acompanhando.*
import kotlinx.android.synthetic.main.activity_agenda.*

class AgendaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)

        if(testConnection() == true) {
            setDataOnline()
        }else{
            Toast.makeText(this, R.string.reportingOffline, Toast.LENGTH_SHORT).show()
            setDataOffline()
        }

        setSupportActionBar(toolbarAgendaPage)

        toolbarAgendaPage.setNavigationOnClickListener {
            callHome()
        }

        floatinButton_agendaPage.setOnClickListener {
            callDescubraPage()
        }
    }

    fun setDataOnline() {
        //Inflando o RecyclerView de Assistir Mais Tarde (FragRecycler_assistirMaisTarde)
        supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragRecycler_assistirMaisTardeSpace,
                FragRecycler_asssistirMaisTarde.newInstance()
            )
            commit()
        }

        //Inflando o RecyclerView de Próximos (FragRecycler_proximosAgenda)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragRecycler_proximosSpace, FragRecycler_proximosAgenda.newInstance())
            commit()
        }
    }

    fun setDataOffline() {
        //Inflando o RecyclerView de Assistir Mais Tarde (FragRecycler_assistirMaisTarde)
        supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.fragRecycler_assistirMaisTardeSpace,
                FragRecycler_asssistirMaisTarde.newInstance()
            )
            commit()
        }
    }

    //Usado para add o Menu a Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    //Usado pra add ações de click aos itens do Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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

    fun callHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    fun callDescubraPage() {
        val intent = Intent(this, DescubraActivity::class.java)
        startActivity(intent)
    }

    fun callConfiguracoesPage() {
        val intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
    }

    fun testConnection(): Boolean {
        val cm = getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }
}