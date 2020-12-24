package com.example.filmapp.home.melhores

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch
import java.math.RoundingMode

class MelhoresFilmesViewModel(val service: Service) : ViewModel() {

    var returnListAPI = MutableLiveData<ArrayList<ResultMovie>>()
    var returnTopMoviesAPI = MutableLiveData<ArrayList<ResultMovie>>()

    fun getTopMoviesList(){
        viewModelScope.launch {
            returnListAPI.value = service.getTopMovies(
                "4a6baee1eff7d3911f03f59b9b8f43eb",
                "en-US"
            ).results

            var list = returnListAPI.value

            returnTopMoviesAPI.value = formatMovie(list)

        }
    }

    fun formatMovie(list: ArrayList<ResultMovie>?): ArrayList<ResultMovie>?{

        list?.forEach {
            var date = it.release_date
            var title = it.title
            var evaluation = it.vote_average

            //Formatação da Data de Lançamento
            var year = "${date?.get(0)}" + "${date?.get(1)}" + "${date?.get(2)}" + "${date?.get(3)}"
            var month = "${date?.get(5)}" + "${date?.get(6)}"
            var day = "${date?.get(8)}" + "${date?.get(9)}"

            when(month){
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

            //Formatação do Título
            if(title.length > 17){
                var newTitle = ""

                for (i in 0..15){
                    newTitle = newTitle + "${title?.get(i)}"
                }

                it.title = newTitle + "..."
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

        return list
    }
}