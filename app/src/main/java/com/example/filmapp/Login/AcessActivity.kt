package com.example.filmapp.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.Home.HomeActivity
import com.example.filmapp.R
import kotlinx.android.synthetic.main.access_body.*
import kotlinx.android.synthetic.main.activity_acess.*

class AcessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acess)

        btnAcessar.setOnClickListener{
            callHome()
        }


        toolbaraccess.setNavigationOnClickListener {
           finish()
        }


    }

    fun callHome(){
        var intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
    fun callLogin(){
        var intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
    }


}