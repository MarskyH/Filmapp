package com.example.filmapp.home.historico

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.dataBase.FilmAppDataBase
import com.example.filmapp.dataBase.HistoricoRepository
import com.example.filmapp.home.acompanhando.realtimeDatabase.AcompanhandoScope
import com.example.filmapp.home.historico.dataBase.HistoricoEntity
import com.example.filmapp.home.historico.realtimeDatabase.HistoricoScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoricoViewModel(app: Application) : AndroidViewModel(app) {

    //Firebase Auth
    val user = FirebaseAuth.getInstance().currentUser

    //Realtime Database
    var USER_ID = user!!.uid
    var cloudDatabase = FirebaseDatabase.getInstance()
    var reference = cloudDatabase.reference

    var returnHistoricoList = MutableLiveData<ArrayList<HistoricoScope>>()

    init {
        if(cloudDatabase == null){
            cloudDatabase = FirebaseDatabase.getInstance()
        }
    }

//Realtime DatabaseInício---------------------------------------------------------------------------

    //Esta function retorna a última lista salva no Realtime Database
    fun getHistoricoInCloud() {
        var historicoList = arrayListOf<HistoricoScope>()

        cloudDatabase.getReference().child("users/${USER_ID}/historico").keepSynced(true)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {

                    if (it.key == "users") {
                        it.children.forEach {
                            if (it.key == USER_ID) {
                                it.children.forEach {
                                    if (it.key == "historico") {
                                        it.children.forEach {

                                            var media = HistoricoScope(
                                                it.child("id").value.toString().toInt(),
                                                it.child("title").value.toString(),
                                                it.child("poster_path").value.toString(),
                                                it.child("type").value.toString(),
                                                it.child("seasonNumber").value.toString().toInt(),
                                                it.child("episodeNumber").value.toString().toInt(),
                                                it.child("formattedTitle").value.toString(),
                                                it.child("episodeTitle").value.toString(),
                                                it.child("formattedEpisodeTitle").value.toString(),
                                                it.child("date").value.toString()
                                            )

                                            historicoList.add(media)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

                returnHistoricoList.value = historicoList
                historicoList = arrayListOf()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Return DB Error:", error.message + " in HistóricoPage")
            }
        })
    }

    fun deleteItemFromHistorico(media: HistoricoScope) {
        cloudDatabase.getReference().child("users/${USER_ID}/historico").keepSynced(true)
        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(USER_ID)
            .child("historico")
            .child(media.id.toString())
            .removeValue()
    }

    fun deleteAllHistorico() {
        cloudDatabase.getReference().child("users/${USER_ID}/historico").keepSynced(true)
        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(USER_ID)
            .child("historico")
            .removeValue()
    }

//Realtime DatabaseFim------------------------------------------------------------------------------

    fun formattingItem(list: ArrayList<HistoricoScope>): ArrayList<HistoricoScope>{

        list.forEach {
            var title = it.title
            var episodeTitle = it.episodeTitle
            var date = it.date

            //Formatação do Título da Série ou Filme
            if(title.length > 13){
                var newTitle = ""

                for (i in 0..12){

                    if (("${title?.get(12)}" == " ") && (i == 12)){
                        break
                    }

                    newTitle = newTitle + "${title?.get(i)}"
                }

                it.formattedTitle = newTitle + "..."
            }else{
                it.formattedTitle = title
            }

            //Formatação do Título do Episódio
            if(episodeTitle.length > 11){
                var newTitle = ""

                for (i in 0..10){

                    if (("${episodeTitle?.get(12)}" == " ") && (i == 12)){
                        break
                    }

                    newTitle = newTitle + "${episodeTitle?.get(i)}"
                }

                it.formattedEpisodeTitle = newTitle + "..."
            }else{
                it.formattedEpisodeTitle = episodeTitle
            }

            //Formatação da Data de Visualização
            if(date.length >= 5) {
                var year = "${date?.get(0)}" + "${date?.get(1)}" + "${date?.get(2)}" + "${date?.get(3)}"
                var month = "${date?.get(5)}" + "${date?.get(6)}"
                var day = "${date?.get(8)}" + "${date?.get(9)}"
                var hour = "${date?.get(11)}" + "${date?.get(12)}"
                var minute = "${date?.get(14)}" + "${date?.get(15)}"

                it.date = day + "/" + month + "/" + year + " às " + hour + ":" + minute

            }
        }

        return list
    }
}