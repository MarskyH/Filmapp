package com.example.filmapp.Configuracoes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filmapp.Login.LoginActivity
import com.example.filmapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_configuracoes.*

lateinit var  listaInfo : ArrayList<String>


private lateinit var mAuth: FirebaseAuth


class ConfiguracoesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracoes)



        setUpTabs()
        toolbarconfiguracoes.setNavigationOnClickListener {
            finish()
        }
        if (getIntent().getBooleanExtra("LOGOUT", false))
        {
            startActivity(Intent(this, LoginActivity::class.java))

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



}