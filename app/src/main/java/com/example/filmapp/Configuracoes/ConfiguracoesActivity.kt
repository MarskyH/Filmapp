package com.example.filmapp.Configuracoes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filmapp.R
import kotlinx.android.synthetic.main.activity_configuracoes.*

class ConfiguracoesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracoes)

        setUpTabs()
        toolbarconfiguracoes.setNavigationOnClickListener {
            finish()
        }

    }

    private fun setUpTabs(){
        val adapter = ViewPagerConfigAdapter(supportFragmentManager)
        adapter.addFragment(PerfilFragment(), "Perfil")
        adapter.addFragment(SegurancaFragment(), "Segurança")
        adapter.addFragment(DesignFragment(), "Design")
        viewPagerConfig.adapter = adapter
        tabsConfig.setupWithViewPager(viewPagerConfig)
    }


}