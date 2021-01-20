package com.example.filmapp.dataBase

import android.content.Context
import androidx.room.*
import com.example.filmapp.home.acompanhando.dataBase.AcompanhandoDAO
import com.example.filmapp.home.acompanhando.dataBase.AcompanhandoEntity
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeDAO
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeEntity
import com.example.filmapp.home.historico.dataBase.DateTypeConverters
import com.example.filmapp.home.historico.dataBase.HistoricoDAO
import com.example.filmapp.home.historico.dataBase.HistoricoEntity

@Database(
    entities = [AssistirMaisTardeEntity::class, AcompanhandoEntity::class, HistoricoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FilmAppDataBase : RoomDatabase() {

    abstract fun assistirMaisTardeDao(): AssistirMaisTardeDAO
    abstract fun acompanhandoDao(): AcompanhandoDAO
    abstract fun historicoDao(): HistoricoDAO

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