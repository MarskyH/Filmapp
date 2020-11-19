package com.example.filmapp.Series.Classes

class Serie(val id: Int, var titulo: String, var temporada: String, var imgTemporada: Int) {
    override fun toString(): String {
        return "Serie(id=$id, titulo='$titulo', temporada='$temporada', imgTemporada=$imgTemporada)"
    }


}

