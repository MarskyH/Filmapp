package com.example.filmapp.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filmapp.Home.HomeActivity
import com.example.filmapp.R
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.sign_in_body.*

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        btnCadastrar.setOnClickListener{
            callHome()
        }
        toolbarregister.setNavigationOnClickListener {
            finish()
        }

    }

    fun callHome(){
        var intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    fun callLogin(){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


}