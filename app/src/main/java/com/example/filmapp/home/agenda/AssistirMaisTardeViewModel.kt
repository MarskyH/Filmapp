package com.example.filmapp.home.agenda

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Services.Service
import com.example.filmapp.dataBase.AssistirMaisTardeRepository
import com.example.filmapp.dataBase.FilmAppDataBase
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeEntity
import com.example.filmapp.home.agenda.realtimeDatabase.AssistirMaisTardeScope
import com.example.filmapp.home.historico.realtimeDatabase.HistoricoScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AssistirMaisTardeViewModel(app: Application): AndroidViewModel(app) {

    //Firebase Auth
    val user = FirebaseAuth.getInstance().currentUser

    //Realtime Database
    var USER_ID = user!!.uid
    var cloudDatabase = FirebaseDatabase.getInstance()
    var reference = cloudDatabase.reference

    var returnAssistirMaisTardeList = MutableLiveData<ArrayList<AssistirMaisTardeScope>>()

    init {
        if(cloudDatabase == null){
            cloudDatabase = FirebaseDatabase.getInstance()
        }
    }

    //Realtime DatabaseInício---------------------------------------------------------------------------

    //Esta function retorna a última lista salva no Realtime Database
    fun getAssistirMaisTardeListInCloud() {
        var assistirMaisTardeList = arrayListOf<AssistirMaisTardeScope>()

        cloudDatabase.getReference().child("users/${USER_ID}/assistirMaisTarde").keepSynced(true)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {

                    if (it.key == "users") {
                        it.children.forEach {
                            if (it.key == USER_ID) {
                                it.children.forEach {
                                    if (it.key == "assistirMaisTarde") {
                                        it.children.forEach {

                                            var media = AssistirMaisTardeScope(
                                                it.child("id").value.toString().toInt(),
                                                it.child("title").value.toString(),
                                                it.child("poster_path").value.toString(),
                                                it.child("type").value.toString(),
                                            )

                                            assistirMaisTardeList.add(media)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

                returnAssistirMaisTardeList.value = assistirMaisTardeList
                assistirMaisTardeList = arrayListOf()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Return DB Error:", error.message + " in AssistirMaisTardeList")
            }
        })
    }

//Realtime DatabaseFim------------------------------------------------------------------------------

}