package com.example.filmapp.Configuracoes

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.filmapp.R
import com.example.filmapp.home.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.net.URI

class EditProfileActivity : AppCompatActivity() {

    private lateinit var fStore: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var storageReference: StorageReference
    private val CODE_IMG = 1000
    lateinit var alertDialog: AlertDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        fStore = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        user = mAuth.currentUser!!
        storageReference = FirebaseStorage.getInstance().getReference(user.uid)


        var nomeAlt: String? = intent.getStringExtra("nomeCompleto")
        var usuarioAlt: String? = intent.getStringExtra("nomeUsuario")
        var email: String? = intent.getStringExtra("email")

        nomeCompletoAlt.setText(nomeAlt)
        nomeDoUsuarioAlt.setText(usuarioAlt)
        emailAltt.setText(email)


//        val profileRef: StorageReference =
//            storageReference.child("users/" + mAuth.currentUser!!.uid + "profile.jpg")
//        profileRef.downloadUrl.addOnSuccessListener { uri->
//            println(uri.toString())
//
//            Picasso.get().load(uri).into(imagemPerfil)
//        }
//
//        changePic.setOnClickListener {
//            Toast.makeText(baseContext, "Botão de trocar foto clicado", Toast.LENGTH_SHORT).show()
//            val openGalleryIntent =
//                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            startActivityForResult(openGalleryIntent, 1000)
//        }

        btnSalvar.setOnClickListener {
            if (nomeCompletoAlt.text.toString().isEmpty() || nomeDoUsuarioAlt.text.toString().isEmpty()
            ) {
                Toast.makeText(baseContext, "Um ou mais campos estão vazios!", Toast.LENGTH_SHORT)
                    .show()
            } else {

//            user.updateEmail(emailAltt.text.toString()).addOnSuccessListener {
//                Toast.makeText(baseContext, "E-mail alterado!", Toast.LENGTH_SHORT).show()
                val docRef: DocumentReference = fStore.collection("users").document(user.uid)
                var editar = mutableMapOf("fName" to nomeDoUsuarioAlt.text.toString())
                editar.put("fName", nomeDoUsuarioAlt.text.toString())
                editar.put("fNomeCom", nomeCompletoAlt.text.toString())
                docRef.update(editar as Map<String, Any>).addOnSuccessListener {
                    Toast.makeText(baseContext, "Dados alterados!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    finish()
                }
            }
//            }.addOnFailureListener{
//                Toast.makeText(baseContext, "Erro! Tente novamente", Toast.LENGTH_SHORT).show()
//
//            }

        }


    }

//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        @androidx.annotation.Nullable data: Intent?
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1000) {
//            if (resultCode == Activity.RESULT_OK) run {
//                var imageUri: Uri? = data?.data
//
//                if (imageUri != null) {
//                    uploadImageToFirebase(imageUri)
//                }
//            }
//        }
//    }


//    fun uploadImageToFirebase(imageUri: Uri) {
//        val fileRef = storageReference.child( "users"+mAuth.currentUser?.uid+"/profile.jpg")
//        fileRef.putFile(imageUri).addOnSuccessListener {
//            fileRef.downloadUrl.addOnSuccessListener { uri ->
//                Picasso.get().load(uri).into(imagemPerfil)
//            }
//        }.addOnFailureListener {
//            Toast.makeText(applicationContext, "Falhou!", Toast.LENGTH_SHORT).show()
//
//        }
//    }


}