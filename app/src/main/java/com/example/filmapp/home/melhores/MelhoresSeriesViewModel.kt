package com.example.filmapp.home.melhores

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.APIConfig.LANGUAGE
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Services.Service
import com.example.filmapp.home.acompanhando.realtimeDatabase.AcompanhandoScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import java.math.RoundingMode

class MelhoresSeriesViewModel(val service: Service) : ViewModel() {

    //Firebase Auth
    val user = FirebaseAuth.getInstance().currentUser

    //Realtime Database
    var USER_ID = user!!.uid
    private var cloudDatabase = FirebaseDatabase.getInstance()
    private var reference = cloudDatabase.reference

    var returnAPI = MutableLiveData<BaseTv>()
    var returnTopSeriesAPI = MutableLiveData<BaseTv>()
    var returnAcompanhandoList = MutableLiveData<ArrayList<AcompanhandoScope>>()
    var list = arrayListOf<AcompanhandoScope>()


    //Firebase Realtime Database--------------------------------------------------------------------

    fun saveInAcompanhandoList(media: ResultTv) {
        var serie =
            AcompanhandoScope(id = media.id, title = media.name, poster_path = media.poster_path)

        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(USER_ID)
            .child("acompanhando")
            .child(media.id.toString())
            .setValue(serie)
    }

    fun deleteFromAcompanhandoList(media: ResultTv) {
        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(USER_ID)
            .child("acompanhando")
            .child(media.id.toString())
            .removeValue()
    }

    //Esta functio retorna a ultima lista salva no Realtime Database
    fun getAcompanhadoList() {
        var acompanhadoList = arrayListOf<AcompanhandoScope>()

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {

                    if (it.key == "users") {
                        it.children.forEach {
                            if (it.key == USER_ID) {
                                it.children.forEach {
                                    if (it.key == "acompanhando") {
                                        it.children.forEach {

                                            var media = AcompanhandoScope(
                                                it.child("id").value.toString().toInt(),
                                                it.child("title").value.toString(),
                                                it.child("poster_path").value.toString(),
                                                it.child("number_of_episodes").value.toString()
                                                    .toInt(),
                                                it.child("number_of_seasons").value.toString()
                                                    .toInt(),
                                                it.child("lastEpisode").value.toString().toInt(),
                                                it.child("nextEpisodeTitle").value.toString(),
                                                it.child("nextEpisodeNumber").value.toString()
                                                    .toInt(),
                                                it.child("totalEpisodesWatched").value.toString()
                                                    .toInt(),
                                                it.child("currentSeason").value.toString().toInt(),
                                                it.child("userProgress").value.toString().toInt(),
                                                it.child("finished").value.toString().toInt()
                                            )

                                            acompanhadoList.add(media)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }


                returnAcompanhandoList.value = acompanhadoList
                acompanhadoList = arrayListOf()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Return DB Error:", error.message + " in Novidades")
            }
        })
    }


    fun checkSerieInAcompanhandoList(
        listAPI: ArrayList<ResultTv>,
        listDataBase: ArrayList<AcompanhandoScope>
    ): ArrayList<ResultTv> {
        var listResult = arrayListOf<ResultTv>()

        listAPI?.forEach {
            var media = it

            listDataBase?.forEach {

                if (media.id == it.id) {
                    media.followingStatusIndication = true
                    media.finished = it.finished
                }
            }

            listResult.add(media)
        }

        return listResult
    }

    //Consumo da API--------------------------------------------------------------------------------

    fun getTopSeriesList() {
        viewModelScope.launch {
            returnAPI.value = service.getTopSeries(
                "4a6baee1eff7d3911f03f59b9b8f43eb",
                LANGUAGE,
                1
            )

            var list = returnAPI.value!!.results

            returnTopSeriesAPI.value = formatSerie(list)

        }
    }

    fun formatSerie(list: ArrayList<ResultTv>): BaseTv? {

        list?.forEach {
            var date = it.first_air_date

            var title = it.name
            if (title == null)
                title = it.original_name

            var evaluation = it.vote_average

            //Formatação da Data de Lançamento
            if (date.length == 10) {
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

                it.first_air_date = day + " de " + month + ", " + year

            }

            //Formatação do Título
            if (title.length > 13) {
                var newTitle = ""

                for (i in 0..12) {

                    if (("${title?.get(12)}" == " ") && (i == 12)) {
                        break
                    }

                    newTitle = newTitle + "${title?.get(i)}"
                }

                it.formattedName = newTitle + "..."
            } else {
                it.formattedName = title
            }

            //Adaptação da Nota - Avaliação
            evaluation = (evaluation / 2)
            evaluation = evaluation.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()

            if ((evaluation >= 0) && (evaluation < 0)) {
                it.numberStars = 0.0
            } else if ((evaluation >= 0.5) && (evaluation < 1)) {
                it.numberStars = 0.5
            } else if ((evaluation >= 1) && (evaluation < 1.5)) {
                it.numberStars = 1.0
            } else if ((evaluation >= 1.5) && (evaluation < 2)) {
                it.numberStars = 1.5
            } else if ((evaluation >= 2) && (evaluation < 2.5)) {
                it.numberStars = 2.0
            } else if ((evaluation >= 2.5) && (evaluation < 3)) {
                it.numberStars = 2.5
            } else if ((evaluation >= 3) && (evaluation < 3.5)) {
                it.numberStars = 3.0
            } else if ((evaluation >= 3.5) && (evaluation < 4)) {
                it.numberStars = 3.5
            } else if ((evaluation >= 4) && (evaluation < 4.5)) {
                it.numberStars = 4.0
            } else if ((evaluation >= 4.5) && (evaluation <= 4.6)) {
                it.numberStars = 4.5
            } else {
                it.numberStars = 5.0
            }

            it.vote_average = evaluation

        }

        var baseTvReturn = BaseTv(
            returnAPI.value!!.page,
            list,
            returnAPI.value!!.total_results,
            returnAPI.value!!.total_pages
        )

        return baseTvReturn
    }
}