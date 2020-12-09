package com.example.filmapp.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filmapp.Home.HomeActivity
import com.example.filmapp.R
import kotlinx.android.synthetic.main.login_body.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener{
            callAcesso()
        }


        btnRegister.setOnClickListener{
            callCadastro()
        }
    }


    fun callCadastro(){
        var intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    fun callAcesso(){
        var intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }










}