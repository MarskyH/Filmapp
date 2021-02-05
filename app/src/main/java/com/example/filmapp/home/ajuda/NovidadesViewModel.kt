package com.example.filmapp.home.ajuda

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmapp.Entities.All.Ajuda
import com.example.filmapp.Services.Service
import com.google.firebase.database.*

class NovidadesViewModel(val service: Service) : ViewModel() {

    //Realtime Database
    var cloudDatabase = FirebaseDatabase.getInstance()
    var reference = cloudDatabase.reference

    var returnNovidades = MutableLiveData<ArrayList<Ajuda>>()

    init {
        if(cloudDatabase == null){
            cloudDatabase = FirebaseDatabase.getInstance()
        }
    }

    fun getNovidadesList(){
        var novidadesList = arrayListOf<Ajuda>()

        cloudDatabase.getReference().child("novidades").keepSynced(true)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {

                    if (it.key == "novidades") {
                        it.children.forEach {

                            var novidade = Ajuda(
                                it.child("title").value.toString(),
                                it.child("body").value.toString(),
                            )

                            novidadesList.add(novidade)
                        }
                    }

                }

                returnNovidades.value = novidadesList
                novidadesList = arrayListOf()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Return DB Error:", error.message + " in Novidades")
            }
        })
    }
}