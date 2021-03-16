package com.example.filmapp.dataBase

import android.content.Context
import androidx.room.*
import com.example.filmapp.Media.dataBase.FavoritosDAO
import com.example.filmapp.Media.dataBase.FavoritosEntity

@Database(
    entities = [FavoritosEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FilmAppDataBase : RoomDatabase() {

    abstract fun favoritosDAO(): FavoritosDAO

    companion object {
        @Volatile
        private var INSTANCE: FilmAppDataBase? = null

        fun getDataBase(context: Context): FilmAppDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FilmAppDataBase::class.java,
                    "filmAppDataBase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}