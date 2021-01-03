package com.example.filmapp.home.melhores

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.agenda.AssistirMaisTardeViewModel
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeEntity
import com.example.filmapp.home.descubra.DescubraMoviesAdapter
import kotlinx.android.synthetic.main.fragment_melhores_filmes.view.*
import kotlin.properties.Delegates

class MelhoresFilmesFragment : Fragment(), MelhoresMoviesAdapter.onMelhoresMovieClickListener {

    private lateinit var melhoresFilmesAdapter: MelhoresMoviesAdapter
    private lateinit var melhoresFilmesLayoutManager: LinearLayoutManager
    private lateinit var viewModelAssistirMaisTarde: AssistirMaisTardeViewModel
    lateinit var listDataBase: List<AssistirMaisTardeEntity>

    val viewModel by viewModels<MelhoresFilmesViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MelhoresFilmesViewModel(service) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_melhores_filmes, container, false)

        viewModelAssistirMaisTarde = ViewModelProvider(this).get(AssistirMaisTardeViewModel::class.java)

        //Iniciando o ReciclerView de Melhores Filmes
        melhoresFilmesLayoutManager = LinearLayoutManager(context)
        melhoresFilmesAdapter = MelhoresMoviesAdapter(this)
        view.rc_melhoresFilmesList.layoutManager = melhoresFilmesLayoutManager
        view.rc_melhoresFilmesList.adapter = melhoresFilmesAdapter
        view.rc_melhoresFilmesList.isHorizontalFadingEdgeEnabled
        view.rc_melhoresFilmesList.setHasFixedSize(true)

        viewModelAssistirMaisTarde.mediaList.observe(viewLifecycleOwner){
            listDataBase = it
        }

        viewModel.returnTopMoviesAPI.observe(viewLifecycleOwner){
            var mediaList = viewModelAssistirMaisTarde.checkMovieInList(it.results, listDataBase)
            melhoresFilmesAdapter.addList(mediaList)
        }

        viewModel.getTopMoviesList()

        return view
    }

    override fun melhoresItemClick(position: Int) {
        viewModel.returnTopMoviesAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            var filme = mediaList.get(position)

            val intent = Intent(context, MediaSelectedActivity::class.java)
            intent.putExtra("poster","https://image.tmdb.org/t/p/w500" + filme.poster_path)
            intent.putExtra("movie",true)
            intent.putExtra("mediaMovie",filme)

            startActivity(intent)
        }
    }

    override fun saveInAssistirMaisTardeList(position: Int) {
        viewModel.returnTopMoviesAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            var media = mediaList.get(position)

            var newEntity = AssistirMaisTardeEntity(media.id, media.title, media.poster_path, "movie")
            viewModelAssistirMaisTarde.saveNewMedia(newEntity)
            Toast.makeText(context, "Filme adicionado a Agenda", Toast.LENGTH_SHORT).show()
        }
    }

    override fun removeOfAssistirMaisTardeList(position: Int) {
        viewModel.returnTopMoviesAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            var media = mediaList.get(position)

            var removeEntity = AssistirMaisTardeEntity(media.id, media.title, media.poster_path, "movie")
            viewModelAssistirMaisTarde.removeMedia(removeEntity)
            Toast.makeText(context, "Filme removido da Agenda", Toast.LENGTH_SHORT).show()
        }
    }

}