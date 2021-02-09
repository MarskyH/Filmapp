package com.example.filmapp.Configuracoes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.filmapp.Login.LoginActivity
import com.example.filmapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_agenda.*
import kotlinx.android.synthetic.main.activity_configuracoes.*

lateinit var  listaInfo : ArrayList<String>


private lateinit var mAuth: FirebaseAuth


class ConfiguracoesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracoes)

        setUpTabs()

        setSupportActionBar(toolbarconfiguracoes)

        toolbarconfiguracoes.setNavigationOnClickListener {
            onBackPressed()
        }

        if (getIntent().getBooleanExtra("LOGOUT", false))
        {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    //Usado para add o Menu a Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_config_page, menu)
        return true
    }

    //Usado pra add ações de click aos itens do Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.reportError_toolbarConfig -> {
                callReportErrorPage()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpTabs(){
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        if(user != null) {
            val adapter = ViewPagerConfigAdapter(supportFragmentManager)
            adapter.addFragment(PerfilFragment(), "Perfil")
            adapter.addFragment(SegurancaFragment(), "Segurança")
//        adapter.addFragment(DesignFragment(), "Design")
            viewPagerConfig.adapter = adapter
            tabsConfig.setupWithViewPager(viewPagerConfig)
        } else {
            val adapter = ViewPagerConfigAdapter(supportFragmentManager)
            adapter.addFragment(PerfilFragment(), "Perfil")
//            adapter.addFragment(SegurancaFragment(), "Segurança")
//        adapter.addFragment(DesignFragment(), "Design")
            viewPagerConfig.adapter = adapter
            tabsConfig.setupWithViewPager(viewPagerConfig)
        }
    }

    fun callReportErrorPage(){
        var intent = Intent(this, ReportErrorActivity::class.java)

        startActivity(intent)
    }

}