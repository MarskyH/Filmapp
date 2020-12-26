package com.example.filmapp.home.melhores

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.descubra.DescubraMoviesAdapter
import kotlinx.android.synthetic.main.fragment_melhores_filmes.view.*

class MelhoresFilmesFragment : Fragment(), MelhoresMoviesAdapter.onMelhoresMovieClickListener {

    private lateinit var melhoresFilmesAdapter: MelhoresMoviesAdapter
    private lateinit var melhoresFilmesLayoutManager: RecyclerView.LayoutManager

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

        //Iniciando o ReciclerView de Melhores Filmes
        melhoresFilmesLayoutManager = LinearLayoutManager(context)
        melhoresFilmesAdapter = MelhoresMoviesAdapter(this)
        view.rc_melhoresFilmesList.layoutManager = melhoresFilmesLayoutManager
        view.rc_melhoresFilmesList.adapter = melhoresFilmesAdapter
        view.rc_melhoresFilmesList.isHorizontalFadingEdgeEnabled
        view.rc_melhoresFilmesList.setHasFixedSize(true)

        viewModel.returnTopMoviesAPI.observe(viewLifecycleOwner){
            melhoresFilmesAdapter.addList(it)
        }

        viewModel.getTopMoviesList()

        return view
    }

    override fun melhoresItemClick(position: Int) {
        viewModel.returnTopMoviesAPI.observe(viewLifecycleOwner){
            var mediaList = it
            var filme = mediaList.get(position)

            val intent = Intent(context, MediaSelectedActivity::class.java)
            intent.putExtra("poster","https://image.tmdb.org/t/p/w500" + filme.poster_path)
            intent.putExtra("movie",true)
            intent.putExtra("mediaMovie",filme)

            startActivity(intent)
        }
    }

}