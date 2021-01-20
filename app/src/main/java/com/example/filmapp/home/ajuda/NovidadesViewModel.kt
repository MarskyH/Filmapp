package com.example.filmapp.home.ajuda

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Classes.Ajuda
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class NovidadesViewModel(val service: Service) : ViewModel() {

    var returnNovidades = MutableLiveData<ArrayList<Ajuda>>()

    fun getNovidadesList(){
        viewModelScope.launch {
            returnNovidades.value = arrayListOf(
                Ajuda(0, "- O que é o Filmapp?", "FilmApp é um aplicativo voltado ao entretenimento com foco em filmes e séries, funcionando como um guia"),
                Ajuda(1, "- O que é o que é?", "Feito para andar e não anda. Resposta: A rua!"),
                Ajuda(2, "- Onde está Wally?", "Where's Wally? é uma série de livros de caráter infanto-juvenil criada pelo ilustrador britânico Martin Handford, baseada em ilustrações e pequenos textos, a série deu origem a uma série animada, uma tira de jornal, uma coleção de 52 revistas semanais intitulada O Mundo de Wally, e jogos eletrônicos."),
                Ajuda(3, "- Onde está Wally?", "Where's Wally? é uma série de livros de caráter infanto-juvenil criada pelo ilustrador britânico Martin Handford, baseada em ilustrações e pequenos textos, a série deu origem a uma série animada, uma tira de jornal, uma coleção de 52 revistas semanais intitulada O Mundo de Wally, e jogos eletrônicos."),
                Ajuda(4, "- Onde está Wally?", "é o ultimo")
            )

        }
    }
}