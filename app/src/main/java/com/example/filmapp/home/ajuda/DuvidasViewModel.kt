package com.example.filmapp.home.ajuda

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Classes.Ajuda
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class DuvidasViewModel(val service: Service) : ViewModel() {

    var returnDuvidas = MutableLiveData<ArrayList<Ajuda>>()

    fun getDuvidasList(){
            returnDuvidas.value = arrayListOf(
                Ajuda(70, "- O que é o Filmapp?", "FilmApp é um aplicativo voltado ao entretenimento com foco em filmes e séries, funcionando como um guia"),
                Ajuda(61, "- O que é o que é?", "Feito para andar e não anda. Resposta: A rua!"),
                Ajuda(72, "- Onde está Wally?", "Where's Wally? é uma série de livros de caráter infanto-juvenil criada pelo ilustrador britânico Martin Handford, baseada em ilustrações e pequenos textos, a série deu origem a uma série animada, uma tira de jornal, uma coleção de 52 revistas semanais intitulada O Mundo de Wally, e jogos eletrônicos."),
                Ajuda(84, "- Onde está Wally?", "é o ultimo"),
                Ajuda(60, "- O que é o Filmapp?", "FilmApp é um aplicativo voltado ao entretenimento com foco em filmes e séries, funcionando como um guia"),
                Ajuda(51, "- O que é o que é?", "Feito para andar e não anda. Resposta: A rua!"),
                Ajuda(62, "- Onde está Wally?", "Where's Wally? é uma série de livros de caráter infanto-juvenil criada pelo ilustrador britânico Martin Handford, baseada em ilustrações e pequenos textos, a série deu origem a uma série animada, uma tira de jornal, uma coleção de 52 revistas semanais intitulada O Mundo de Wally, e jogos eletrônicos."),
                Ajuda(43, "- Onde está Wally?", "Where's Wally? é uma série de livros de caráter infanto-juvenil criada pelo ilustrador britânico Martin Handford, baseada em ilustrações e pequenos textos, a série deu origem a uma série animada, uma tira de jornal, uma coleção de 52 revistas semanais intitulada O Mundo de Wally, e jogos eletrônicos."),
                Ajuda(74, "- Onde está Wally?", "é o ultimo")
            )
    }
}