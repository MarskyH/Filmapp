package com.example.filmapp.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.filmapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_recuperar_senha.*

class RecuperarSenhaActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_senha)

        toolbarEsquecesenha.setNavigationOnClickListener {
            finish()
        }

        btnEnviar.setOnClickListener{
            recSenha()

        }



    }


    fun recSenha(){
        val email = edEmailSenhaEsquecida.text.toString()

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Email enviado!", Toast.LENGTH_LONG).show()
                        callLogin()

                    } else {
                        Toast.makeText(this, "Erro! Digite novamente.", Toast.LENGTH_LONG).show()
                    }
                }

            }


    }


    fun callLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


}