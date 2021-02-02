package com.example.filmapp.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.filmapp.home.HomeActivity
import com.example.filmapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlin.collections.HashMap

class SignInActivity : AppCompatActivity() {

    private lateinit var fStore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        fStore = FirebaseFirestore.getInstance()


//        toolbarregister.setNavigationOnClickListener {
//            finish()
//        }

        btnCadastrar.setOnClickListener {



            var userName = edUsuario.text.toString()
            var nomeC = edNomeCompleto.text.toString()
            var cel = edCelular.text.toString()
            var email = edEmail.text.toString()
            var password = edSenha.text.toString()


            var rEdUser = email.isNotEmpty()
            var rPassUser = password.isNotEmpty()
            var rUsario = userName.isNotEmpty()
            var rNome = nomeC.isNotEmpty()
            var rCel =  cel.isNotEmpty()

            if (password == senhaConfirmar.text.toString()) {
                if (rEdUser && rPassUser && rUsario && rNome && rCel)
                    registerFirebase(email, password, userName, nomeC, cel)
                else
                    showMsg("Preencha todos os campos")

            } else {
                showMsg("As senhas estão diferentes.")

            }
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


    fun registerFirebase(email: String, password: String, username: String, nomeC: String, cel: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result?.user!!
                    showMsg("usuário registrado com sucesso")
                    var idUser = firebaseUser.uid
                    var email = firebaseUser.email
                    var documentReference: DocumentReference = fStore.collection("users").document(idUser)

                    var user = mutableMapOf("fName" to username)
                    user.put("fName", username)
                    if (email != null) {
                        user.put("email", email)
                    }
                    user.put("fNomeCom", nomeC)
                    user.put("fCel", cel)

                    documentReference.set(user).addOnSuccessListener(this){ task ->
                            println("signInWithCredential:success")

                    }


                    callMain(idUser.toString(), email.toString())
                } else {
                    showMsg(task.exception?.message.toString())
                }

            }
    }

    fun callMain(id: String, email: String){
        var intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("user_id", id)
        intent.putExtra("user_email", email)
        startActivity(intent)
        finish()
    }


    fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


}

private fun <K, V> HashMap<K, V>.put(key: K, value: K) {

}




