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
    lateinit var cloudDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference

    var returnNovidades = MutableLiveData<ArrayList<Ajuda>>()

    fun getNovidadesList(){
        var novidadesList = arrayListOf<Ajuda>()

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

    fun conectDatabase() {
        cloudDatabase = FirebaseDatabase.getInstance()
        reference = cloudDatabase.reference
    }
}