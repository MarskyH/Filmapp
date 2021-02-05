package com.example.filmapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.filmapp.Login.LoginActivity
import com.example.filmapp.dataBase.FilmAppDataBase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity: AppCompatActivity() {
    val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_splash)

        var firebase = FirebaseDatabase.getInstance().setPersistenceEnabled(true)
//        if(FirebaseAuth.getInstance().currentUser == null){
//            try {
//                FirebaseDatabase.getInstance().reference
//                    .child("users")
//                    .child("1")
//                    .removeValue()
//            }catch (e: Exception){
//                Log.i("Usuário existe", "Login com sucesso")
//            }
//        }

        splashCoroutine()
    }

    fun splashCoroutine(){
        val intent = Intent(this, LoginActivity::class.java)
        scope.launch {
            delay(2000)

            startActivity(intent)
            finish()
        }
    }
}