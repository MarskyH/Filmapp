package com.example.filmapp.home.melhores

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.APIConfig.LANGUAGE
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Services.Service
import com.example.filmapp.home.agenda.realtimeDatabase.AssistirMaisTardeScope
import com.example.filmapp.home.historico.realtimeDatabase.HistoricoScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.time.LocalDateTime

class MelhoresFilmesViewModel(val service: Service) : ViewModel() {

    //Firebase Auth
    val user = FirebaseAuth.getInstance().currentUser

    //Realtime Database
    var USER_ID = user!!.uid
    var cloudDatabase = FirebaseDatabase.getInstance()
    var reference = cloudDatabase.reference

    var returnAPI = MutableLiveData<BaseMovie>()
    var returnTopMoviesAPI = MutableLiveData<BaseMovie>()
    var returnAssistirMaisTardeList = MutableLiveData<ArrayList<AssistirMaisTardeScope>>()
    var returnHistoricoList = MutableLiveData<ArrayList<HistoricoScope>>()

    //Realtime DatabaseInício-----------------------------------------------------------------------

    fun saveInHistoricoList(media: ResultMovie){

        //Verificando se o usuário possui um dipositivo com a versão do android compatível
        var currentDateTime = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime.now().toString()
        } else {
            "404"
        }

        var movie = HistoricoScope(media.id, media.title, media.poster_path, "Movie", date = currentDateTime)

        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(USER_ID)
            .child("historico")
            .child(media.id.toString())
            .setValue(movie)
    }

    fun deleteFromHistoricoList(media: ResultMovie) {
        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(USER_ID)
            .child("historico")
            .child(media.id.toString())
            .removeValue()
    }

    //Esta function retorna o último histórico salvo no Realtime Database
    fun getHistoricoInCloud() {
        var historicoList = arrayListOf<HistoricoScope>()

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

    fun checkMovieInHistorico(
        listAPI: ArrayList<ResultMovie>,
        listDataBase: ArrayList<HistoricoScope>
    ): ArrayList<ResultMovie> {
        var listResult = arrayListOf<ResultMovie>()

        listAPI?.forEach {
            var media = it

            listDataBase?.forEach {

                if (media.id == it.id)
                    media.watched = true
            }

            listResult.add(media)
        }

        return listResult
    }

    fun saveInAssistirMaisTardeList(media: ResultMovie){
        var movie =
            AssistirMaisTardeScope(id = media.id, title = media.title, poster_path = media.poster_path, "Movie")

        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(USER_ID)
            .child("assistirMaisTarde")
            .child(media.id.toString())
            .setValue(movie)
    }

    fun deleteFromAssistirMaisTardeList(media: ResultMovie) {
        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(USER_ID)
            .child("assistirMaisTarde")
            .child(media.id.toString())
            .removeValue()
    }

    //Esta function retorna a última lista salva no Realtime Database
    fun getAssistirMaisTardeListInCloud() {
        var assistirMaisTardeList = arrayListOf<AssistirMaisTardeScope>()

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
                Log.i("Return DB Error:", error.message + " in MelhoresFilmesList")
            }
        })
    }

    fun checkInAssistirMaisTardeList(
        listAPI: ArrayList<ResultMovie>,
        listDataBase: ArrayList<AssistirMaisTardeScope>
    ): ArrayList<ResultMovie> {
        var listResult = arrayListOf<ResultMovie>()

        listAPI?.forEach {
            var media = it

            listDataBase?.forEach {

                if (media.id == it.id)
                    media.assistirMaisTardeIndication = true
            }

            listResult.add(media)
        }

        return listResult
    }

    //Realtime DatabaseFim--------------------------------------------------------------------------

    fun getTopMoviesList(){
        viewModelScope.launch {
            returnAPI.value = service.getTopMovies(
                "4a6baee1eff7d3911f03f59b9b8f43eb",
                LANGUAGE
            )

            var list = returnAPI.value!!.results

            returnTopMoviesAPI.value = formatMovie(list)

        }
    }

    fun formatMovie(list: ArrayList<ResultMovie>): BaseMovie?{

        list?.forEach {
            var date = it.release_date
            var title = it.title
            var evaluation = it.vote_average

            //Formatação da Data de Lançamento
            if(date.length == 10) {
                var year =
                    "${date?.get(0)}" + "${date?.get(1)}" + "${date?.get(2)}" + "${date?.get(3)}"
                var month = "${date?.get(5)}" + "${date?.get(6)}"
                var day = "${date?.get(8)}" + "${date?.get(9)}"

                when (month) {
                    "01" -> month = "Janeiro"
                    "02" -> month = "Fevereiro"
                    "03" -> month = "Março"
                    "04" -> month = "Abril"
                    "05" -> month = "Maio"
                    "06" -> month = "Junho"
                    "07" -> month = "Julho"
                    "08" -> month = "Agosto"
                    "09" -> month = "Setembro"
                    "10" -> month = "Outubro"
                    "11" -> month = "Novembro"
                    "12" -> month = "Dezembro"
                    else -> month = " "
                }

                it.release_date = day + " de " + month + ", " + year

            }

            //Formatação do Título
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

            //Adaptação da Nota - Avaliação
            evaluation = (evaluation/2)
            evaluation = evaluation.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()

            if ((evaluation >= 0) && (evaluation < 0)){
                it.numberStars = 0.0
            }else if ((evaluation >= 0.5) && (evaluation < 1)){
                it.numberStars = 0.5
            }else if ((evaluation >= 1) && (evaluation < 1.5)){
                it.numberStars = 1.0
            }else if ((evaluation >= 1.5) && (evaluation < 2)){
                it.numberStars = 1.5
            }else if ((evaluation >= 2) && (evaluation < 2.5)){
                it.numberStars = 2.0
            }else if ((evaluation >= 2.5) && (evaluation < 3)){
                it.numberStars = 2.5
            }else if ((evaluation >= 3) && (evaluation < 3.5)){
                it.numberStars = 3.0
            }else if ((evaluation >= 3.5) && (evaluation < 4)){
                it.numberStars = 3.5
            }else if ((evaluation >= 4) && (evaluation < 4.5)){
                it.numberStars = 4.0
            }else if ((evaluation >= 4.5) && (evaluation <= 4.6)){
                it.numberStars = 4.5
            }else{
                it.numberStars = 5.0
            }

            it.vote_average = evaluation

        }

        var baseMovieReturn = BaseMovie(returnAPI.value!!.page, list, returnAPI.value!!.total_results, returnAPI.value!!.total_pages)

        return baseMovieReturn
    }
}