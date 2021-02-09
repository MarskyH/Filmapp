package com.example.filmapp.Configuracoes

import androidx.lifecycle.ViewModel
import com.example.filmapp.Configuracoes.realtimeDatabase.ErrorReportScope
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Services.Service
import com.example.filmapp.home.agenda.realtimeDatabase.AssistirMaisTardeScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ReportErrorViewModel() : ViewModel() {

    //Firebase Auth
    val user = FirebaseAuth.getInstance().currentUser

    //Realtime Database
    var USER_ID = user!!.uid
    var cloudDatabase = FirebaseDatabase.getInstance()
    var reference = cloudDatabase.reference

    init {
        if(cloudDatabase == null){
            cloudDatabase = FirebaseDatabase.getInstance()
        }
    }

    fun sendErrorReport(error: ErrorReportScope) {

        FirebaseDatabase.getInstance().reference
            .child("developers")
            .child("errosReportados")
            .push()
            .setValue(error)
    }
}