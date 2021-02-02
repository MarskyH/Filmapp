package com.example.filmapp.Configuracoes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.filmapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_perfil.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var fStore: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        fStore = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        var idUser = mAuth.currentUser?.uid


        var nomeAlt: String? = intent.getStringExtra("nomeCompleto")
        var usuarioAlt: String? = intent.getStringExtra("nomeUsuario")
        var telefone: String? = intent.getStringExtra("telefone")

        nomeCompletoAlt.setText(telefone)
        nomeDoUsuarioAlt.setText(usuarioAlt)
        telefoneAlt.setText(telefone)



        btnSalvar.setOnClickListener {
            if(nomeCompletoAlt.text.toString().isEmpty() || nomeDoUsuarioAlt.text.toString().isEmpty() || telefoneAlt.text.toString().isEmpty()){
                Toast.makeText(baseContext, "Um ou mais campos estão vazios!", Toast.LENGTH_SHORT).show()

            }

        }



    }
}