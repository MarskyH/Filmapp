package com.example.filmapp.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.filmapp.Home.HomeActivity
import com.example.filmapp.R
<<<<<<< HEAD
import com.example.filmapp.home.HomeActivity
=======
>>>>>>> Marcus
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLoginAct.setOnClickListener{
            callAcesso()
        }


        btnRegisterAct.setOnClickListener{
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