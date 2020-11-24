package com.example.filmapp.Filmes.Classes

class Filme(val id: Int, var titulo: String, var imgFilme: Int) {
    override fun toString(): String {
        return "Filme(id=$id, titulo='$titulo', imgFilme=$imgFilme)"
    }


}

