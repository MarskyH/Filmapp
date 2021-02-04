package com.example.filmapp.Configuracoes

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.view.isInvisible
import com.example.filmapp.BuildConfig
import com.example.filmapp.Login.LoginActivity
import com.example.filmapp.R
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.GoogleSignatureVerifier
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.internal.GoogleApiManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.fragment_perfil.*
import kotlinx.android.synthetic.main.fragment_perfil.view.*
import kotlinx.android.synthetic.main.fragment_seguranca.view.*
import kotlinx.android.synthetic.main.fragment_seguranca.view.btnDesativarConta
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PerfilFragment : Fragment() {

    private lateinit var fStore: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    var uri: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_perfil, container, false)

        fStore = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance().getReference()

        val profileRef: StorageReference = storageReference.child("users/"+mAuth.currentUser!!.uid+"profile.jpg")
        profileRef.downloadUrl.addOnSuccessListener {uri->
            println(uri.toString())

            Picasso.get().load(uri).into(imagemPer)
        }



        var user = mAuth.currentUser
        var idUser = mAuth.currentUser?.uid

        println("Email: "+Constants.emailGoogle)


        if(idUser == null){
            view.btnAlterar.isInvisible
            emailVis.isInvisible
            nomeCompleto.isInvisible
            nomeDoUsuario.isInvisible
            viewNomeUsuario.isInvisible
            viewNomeCompleto.isInvisible

        }


        if (idUser != null) {
            val documentReference: DocumentReference = fStore.collection("users").document(idUser)
            documentReference.addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
//                    Toast.makeText(context, snapshot.getString("fCel"), Toast.LENGTH_LONG).show()
                    emailVis.setText(snapshot.getString("email"))
                    nomeCompleto.setText(snapshot.getString("fNomeCom"))
                    nomeDoUsuario.setText(snapshot.getString("fName"))
                    if(emailVis.text.toString().isEmpty() && nomeCompleto.text.toString().isEmpty() && nomeDoUsuario.text.toString().isEmpty() && !isLoggedIn()){
                        Constants.loginGoogle = true
                        emailVis.setText(user?.email)
                        nomeCompleto.setText(user?.displayName)
//                        nomeDoUsuario.setText("Login com google não tem usuário")
                        viewNomeUsuario.isInvisible
                        nomeDoUsuario.isInvisible
                    } else if(emailVis.text.toString().isEmpty() && nomeCompleto.text.toString().isEmpty() && nomeDoUsuario.text.toString().isEmpty() && isLoggedIn()){
                        emailVis.setText(user?.email)
                        nomeCompleto.setText(user?.displayName)
//                        nomeDoUsuario.setText("Login com Facebook não tem usuário")
                        viewNomeUsuario.isInvisible
                        nomeDoUsuario.isInvisible
                    }

                }
            }
        }
        view.btnSairDaConta.setOnClickListener {
            if (isLoggedIn()) {
                Constants.loginGoogle = false
                LoginManager.getInstance().logOut()
                Firebase.auth.signOut()
                Toast.makeText(context, "Deslogado do Facebook!.", Toast.LENGTH_LONG).show()

            } else {
                Constants.loginGoogle = false
                Firebase.auth.signOut()
//            LoginManager.getInstance().logOut()
                Toast.makeText(context, "Deslogado!.", Toast.LENGTH_LONG).show()
            }
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)


        }

        view.btnAlterar.setOnClickListener {
            if(idUser != null){
                if(Constants.loginGoogle == true || isLoggedIn()){
                    Toast.makeText(context, "Só é possivel alterar os dados tendo uma conta diretamente no nosso aplicativo.", Toast.LENGTH_LONG).show()
                }
                else if(!Constants.loginGoogle && !isLoggedIn()){
                    val intent = Intent(context, EditProfileActivity::class.java)
                    intent.putExtra("nomeCompleto", nomeCompleto.text.toString())
                    intent.putExtra("email", emailVis.text.toString())
                    intent.putExtra("nomeUsuario", nomeDoUsuario.text.toString())
                    startActivity(intent)
                }




            }
        }

        return view
    }


//    fun updateProfile(){
//        Firebase.auth.currentUser?.let {
//            val username = usarioNome.text.toString()
//            val profileUpdate = UserProfileChangeRequest.Builder()
//                .displayName(username)
//
//        }
//    }

    fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired



        return isLoggedIn
    }






    }
