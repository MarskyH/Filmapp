package com.example.filmapp.Classes

import java.io.Serializable

class Usuario constructor(val id: Int,  var userName: String, var senha: String): Serializable{

    override fun toString(): String {
        return "Usuario(id=$id, userName='$userName', senha='$senha')"
    }
}