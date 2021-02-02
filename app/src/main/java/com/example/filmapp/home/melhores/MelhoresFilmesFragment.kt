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
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.agenda.AssistirMaisTardeViewModel
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeEntity
import com.example.filmapp.home.historico.HistoricoViewModel
import com.example.filmapp.home.historico.dataBase.HistoricoEntity
import kotlinx.android.synthetic.main.fragment_melhores_filmes.*
import kotlinx.android.synthetic.main.fragment_melhores_filmes.view.*
import java.time.LocalDateTime

class MelhoresFilmesFragment : Fragment(), MelhoresMoviesAdapter.onMelhoresMovieClickListener {

    private lateinit var melhoresFilmesAdapter: MelhoresMoviesAdapter
    private lateinit var melhoresFilmesLayoutManager: LinearLayoutManager
    private lateinit var viewModelAssistirMaisTarde: AssistirMaisTardeViewModel
    private lateinit var viewModelHistorico: HistoricoViewModel
    var listAssistirMaisTardeDataBase = listOf<AssistirMaisTardeEntity>()
    var listHistoricoDataBase = listOf<HistoricoEntity>()

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
        viewModelHistorico = ViewModelProvider(this).get(HistoricoViewModel::class.java)

        //Iniciando o ReciclerView de Melhores Filmes
        melhoresFilmesLayoutManager = LinearLayoutManager(context)
        melhoresFilmesAdapter = MelhoresMoviesAdapter(this)
        view.rc_melhoresFilmesList.layoutManager = melhoresFilmesLayoutManager
        view.rc_melhoresFilmesList.adapter = melhoresFilmesAdapter
        view.rc_melhoresFilmesList.isHorizontalFadingEdgeEnabled
        view.rc_melhoresFilmesList.setHasFixedSize(true)

        //Recebendo os Itens (Filmes e Séries) que estão na lista de Assistir Mais Tarde
        viewModelAssistirMaisTarde.mediaList.observe(viewLifecycleOwner){
            listAssistirMaisTardeDataBase = it
        }

        //Recebendo os Filmes que o usuário já assistiu
        viewModelHistorico.mediaList.observe(viewLifecycleOwner){
            listHistoricoDataBase = it
        }

        viewModel.returnTopMoviesAPI.observe(viewLifecycleOwner){
            var mediaList = viewModelAssistirMaisTarde.checkMovieInList(it.results, listAssistirMaisTardeDataBase)
            mediaList = viewModel.checkMovieInHistorico(it.results, listHistoricoDataBase)
            pb_melhoresFilmes.setVisibility(View.INVISIBLE)
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
            intent.putExtra("id", filme.id)

            startActivity(intent)
        }
    }

    override fun saveInAssistirMaisTardeList(position: Int) {
        viewModel.returnTopMoviesAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            var media = mediaList.get(position)

            var newEntity = AssistirMaisTardeEntity(media.id, media.title, media.poster_path, "Movie")
            viewModelAssistirMaisTarde.saveNewMedia(newEntity)
            Toast.makeText(context, "Filme adicionado a Agenda", Toast.LENGTH_SHORT).show()
        }
    }

    override fun removeOfAssistirMaisTardeList(position: Int) {
        viewModel.returnTopMoviesAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            var media = mediaList.get(position)

            var removeEntity = AssistirMaisTardeEntity(media.id, media.title, media.poster_path, "Movie")
            viewModelAssistirMaisTarde.removeMedia(removeEntity)
            Toast.makeText(context, "Filme removido da Agenda", Toast.LENGTH_SHORT).show()
        }
    }

    override fun saveInHistorico(position: Int) {
        viewModel.returnTopMoviesAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            var media = mediaList.get(position)

            //Verificando se o usuário possui um dipositivo com a versão do android compatível
            var currentDateTime = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDateTime.now().toString()
            } else {
                " "
            }

            var newItem = HistoricoEntity(media.id, media.title, media.poster_path, "Movie", date = currentDateTime)
            viewModelHistorico.saveNewItem(newItem)
            Toast.makeText(context, "O filme ${media.title} foi adicionado ao Histórico", Toast.LENGTH_SHORT).show()
        }
    }

    override fun removeOfHistorico(position: Int) {
        viewModel.returnTopMoviesAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            var media = mediaList.get(position)

            var item = HistoricoEntity(media.id, media.title, media.poster_path, "Movie")
            viewModelHistorico.removeItem(item)
            Toast.makeText(context, "O filme ${media.title} foi removido do Histórico", Toast.LENGTH_SHORT).show()
        }
    }

    override fun share(position: Int) {
        viewModel.returnTopMoviesAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            var media = mediaList.get(position)

            var ShareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(
                    Intent.EXTRA_TEXT,
                    "${media.title} está em ${position + 1}º na lista de Melhores Filmes do Filmapp!"
                )
                this.type = "text/plain"
            }
            startActivity(ShareIntent)
        }
    }

}