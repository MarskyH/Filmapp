package com.example.filmapp.home.emAlta

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
import kotlinx.android.synthetic.main.fragrecycler_emcartaz.*
import kotlinx.android.synthetic.main.fragrecycler_novosepisodios.*
import kotlinx.android.synthetic.main.fragrecycler_novosepisodios.view.*
class FragRecycler_novosEpisodios : Fragment(),
    NovosEpisodiosAdapter.onNovosEpisodiosItemClickListener {

    private lateinit var mediaListAdapter: NovosEpisodiosAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager

    val viewModel by viewModels<NovosEpisodiosViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NovosEpisodiosViewModel(service) as T
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
        var view = inflater.inflate(R.layout.fragrecycler_novosepisodios, container, false)

        //Iniciando o ReciclerView Novos Episodios
        mediaListLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mediaListAdapter = NovosEpisodiosAdapter(this)
        view.rv_novosEpisodeos_EmAlta.layoutManager = mediaListLayoutManager
        view.rv_novosEpisodeos_EmAlta.adapter = mediaListAdapter
        view.rv_novosEpisodeos_EmAlta.isHorizontalFadingEdgeEnabled
        view.rv_novosEpisodeos_EmAlta.setHasFixedSize(true)

        viewModel.returnNovosEpisodiosListAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            pb_novosEpisodios.setVisibility(View.INVISIBLE)
            mediaListAdapter.addList(mediaList)
        }

        viewModel.getNovosEpisodiosList()

        return view
    }

    companion object{
        fun newInstance() = FragRecycler_novosEpisodios()
    }

    override fun novosEpisodiosItemClick(position: Int) {
        viewModel.returnNovosEpisodiosListAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            var media = mediaList.get(position)

            val intent = Intent(context, MediaSelectedActivity::class.java)
            intent.putExtra("poster","https://image.tmdb.org/t/p/w500" + media.poster_path)
            intent.putExtra("movie",false)
            intent.putExtra("mediaSerie", media)

            startActivity(intent)
        }
    }

}