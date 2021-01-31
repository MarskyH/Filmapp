package com.example.filmapp.home.acompanhando

import androidx.lifecycle.ViewModel
import com.example.filmapp.Services.Service
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AcompanhandoViewModelTeste(val service: Service) : ViewModel() {

    //Realtime Database
    lateinit var cloudDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference

    fun conectDatabase(){
        cloudDatabase = FirebaseDatabase.getInstance()
        reference = cloudDatabase.reference
    }
}