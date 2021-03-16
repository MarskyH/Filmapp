package com.example.filmapp.home.historico

import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.Configuracoes.ReportErrorActivity
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.home.HomeActivity
import com.example.filmapp.home.descubra.DescubraActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_historico.*
import kotlinx.android.synthetic.main.custom_alert.view.*

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

        if(testConnection() == true) {
            setDataOnline()
        }else{
            Toast.makeText(this, R.string.reportingOffline, Toast.LENGTH_SHORT).show()
            setDataOffline()
        }

        setSupportActionBar(toolbarHistoricoPage)

        toolbarHistoricoPage.setNavigationOnClickListener {
            callHome()
        }
    }

    fun setDataOnline(){
        mediaListAdapter.isClickable = true

            viewModel.returnHistoricoList.observe(this) {
                try {
                var mediaList = viewModel.formattingItem(it)
                pb_historico.setVisibility(View.INVISIBLE)
                mediaListAdapter.addList(mediaList)
                }catch (e : Exception) {
                    creatAlertException(e, "viewModel.returnHistoricoList.observe")
                }
            }

        viewModel.getHistoricoInCloud()
    }

    fun setDataOffline(){
        mediaListAdapter.isClickable = false

        viewModel.returnHistoricoList.observe(this){
            try {
            var mediaList = viewModel.formattingItem(it)
            pb_historico.setVisibility(View.INVISIBLE)
            mediaListAdapter.addList(mediaList)
            }catch (e : Exception) {
                creatAlertException(e, "viewModel.returnHistoricoList.observe")
            }
        }

        viewModel.getHistoricoInCloud()
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

    override fun historicoItemLongClick(position: Int) {
        var mediaList = mediaListAdapter.mediaList
        var media = mediaList.get(position)
        //Inserir código....
    }

    fun testConnection(): Boolean {
        val cm = getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected

    }

        fun creatAlertException(e: Exception, errorComponent: String) {

            val builder = AlertDialog.Builder(this).create()
            val view: View = LayoutInflater.from(this).inflate(R.layout.custom_alert_erro, null)

            builder.setView(view)
            builder.show()

            //Caso o usuário queira reportar o Erro
            view.btAlert_confirm.setOnClickListener {
                var intent = Intent(this, ReportErrorActivity::class.java)
                intent.putExtra("isForwarded", true)
                intent.putExtra("errorLocation", "Histórico")
                intent.putExtra("errorComponent", errorComponent)
                intent.putExtra("error", e.toString())

                startActivity(intent)
                builder.dismiss()
                finish()
            }

            view.btAlert_Notconfirm.setOnClickListener {
                builder.dismiss()
                finish()
            }
        }

}