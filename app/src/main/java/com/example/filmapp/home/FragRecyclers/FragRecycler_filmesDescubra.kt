package com.example.filmapp.home.FragRecyclers

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
import com.example.filmapp.home.adapters.RecyclerViews.DescubraListsAdapter
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.FragRecyclers.viewmodels.FilmesDescubraViewModel
import kotlinx.android.synthetic.main.fragrecycler_filmesdescubra.view.*

class FragRecycler_filmesDescubra : Fragment(), DescubraListsAdapter.onDescubraItemClickListener {

    private lateinit var mediaListAdapter: DescubraListsAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager

    val viewModel by viewModels<FilmesDescubraViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return FilmesDescubraViewModel(service) as T
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
        var view = inflater.inflate(R.layout.fragrecycler_filmesdescubra, container, false)

        //Iniciando o ReciclerView Descubra - Filmes
        mediaListLayoutManager = LinearLayoutManager(context)
        mediaListAdapter = DescubraListsAdapter(this)
        view.rv_filmesDescubra.layoutManager = mediaListLayoutManager
        view.rv_filmesDescubra.adapter = mediaListAdapter
        view.rv_filmesDescubra.isHorizontalFadingEdgeEnabled
        view.rv_filmesDescubra.setHasFixedSize(true)

//        viewModel.returnDescubraFilmesListAPI.observe(this){
//            mediaListAdapter.addList(it)
//        }

        return view
    }

    companion object{
        fun newInstance() = FragRecycler_filmesDescubra()
    }

    override fun descubraItemClick(position: Int) {
//        val filme = filmesList.get(position)

        val intent = Intent(context, MediaSelectedActivity::class.java)
        startActivity(intent)
    }

}