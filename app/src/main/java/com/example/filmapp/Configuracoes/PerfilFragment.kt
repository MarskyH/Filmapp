package com.example.filmapp.Configuracoes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import com.example.filmapp.Login.LoginActivity
import com.example.filmapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.fragment_perfil.*
import kotlinx.android.synthetic.main.fragment_perfil.view.*
import kotlinx.android.synthetic.main.fragment_seguranca.view.*
import kotlinx.android.synthetic.main.fragment_seguranca.view.btnDesativarConta


class PerfilFragment : Fragment() {

    private lateinit var fStore: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_perfil, container, false)

        fStore = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        var idUser = mAuth.currentUser?.uid


        if(idUser != null){
            val documentReference: DocumentReference = fStore.collection("users").document(idUser)
            documentReference.addSnapshotListener{snapshot, e ->
                if (snapshot != null) {
//                    Toast.makeText(context, snapshot.getString("fCel"), Toast.LENGTH_LONG).show()
                    telefone.setText(snapshot.getString("fCel"))
                    nomeCompleto.setText(snapshot.getString("fNomeCom"))
                    nomeDoUsuario.setText(snapshot.getString("fName"))
                }
            }
        }

        view.btnSairDaConta.setOnClickListener {
            Firebase.auth.signOut()
//            LoginManager.getInstance().logOut()
            Toast.makeText(context, "Deslogado!.", Toast.LENGTH_LONG).show()

            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)









        }

        view.btnAlterar.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            intent.putExtra("nomeCompleto", nomeCompleto.text.toString())
            intent.putExtra("telefone", telefone.text.toString())
            intent.putExtra("nomeUsuario", nomeDoUsuario.text.toString())
            startActivity(intent)

        }

        return view    }




//    fun updateProfile(){
//        Firebase.auth.currentUser?.let {
//            val username = usarioNome.text.toString()
//            val profileUpdate = UserProfileChangeRequest.Builder()
//                .displayName(username)
//
//        }
//    }







}