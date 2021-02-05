package com.example.filmapp.home.ajuda

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmapp.Entities.All.Ajuda
import com.example.filmapp.Services.Service
import com.google.firebase.database.*

class DuvidasViewModel(val service: Service) : ViewModel() {

    //Realtime Database
    var cloudDatabase = FirebaseDatabase.getInstance()
    var reference = cloudDatabase.reference

    var returnDuvidas = MutableLiveData<ArrayList<Ajuda>>()

    init {
        if(cloudDatabase == null){
            cloudDatabase = FirebaseDatabase.getInstance()
        }
    }

    fun getDuvidasList(){
        var duvidasList = arrayListOf<Ajuda>()

        cloudDatabase.getReference().child("duvidas").keepSynced(true)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {

                    if (it.key == "duvidas") {
                        it.children.forEach {

                            var duvida = Ajuda(
                                it.child("title").value.toString(),
                                it.child("body").value.toString(),
                            )

                            duvidasList.add(duvida)
                        }
                    }

                }

                returnDuvidas.value = duvidasList
                duvidasList = arrayListOf()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Return DB Error:", error.message + " in Duvidas")
            }
        })
    }
}