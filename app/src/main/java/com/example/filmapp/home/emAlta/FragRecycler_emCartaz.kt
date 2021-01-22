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
import kotlinx.android.synthetic.main.fragrecycler_emcartaz.view.*
import kotlinx.android.synthetic.main.fragrecycler_melhoresdasemana.*

class FragRecycler_emCartaz : Fragment(), EmCartazAdapter.onEmCartazItemClickListener {

    private lateinit var mediaListAdapter: EmCartazAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager

    val viewModel by viewModels<EmCartazViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return EmCartazViewModel(service) as T
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
        var view = inflater.inflate(R.layout.fragrecycler_emcartaz, container, false)

        //Iniciando o ReciclerView Em Cartaz
        mediaListLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mediaListAdapter = EmCartazAdapter(this)
        view.rv_emCartaz_EmAlta.layoutManager = mediaListLayoutManager
        view.rv_emCartaz_EmAlta.adapter = mediaListAdapter
        view.rv_emCartaz_EmAlta.isHorizontalFadingEdgeEnabled
        view.rv_emCartaz_EmAlta.setHasFixedSize(true)

        viewModel.returnEmCartazAPI.observe(viewLifecycleOwner) {
            var mediaList = it.results
            pb_emCartaz.setVisibility(View.INVISIBLE)
            mediaListAdapter.addList(mediaList)
        }

        viewModel.getEmCartazList()

        return view
    }

    companion object {
        fun newInstance() = FragRecycler_emCartaz()
    }

    override fun emCartazItemClick(position: Int) {
        viewModel.returnEmCartazAPI.observe(viewLifecycleOwner) {
            var mediaList = it.results
            var filme = mediaList.get(position)

            val intent = Intent(context, MediaSelectedActivity::class.java)
            intent.putExtra("poster","https://image.tmdb.org/t/p/w500" + filme.poster_path)
            intent.putExtra("movie",true)
            intent.putExtra("id", filme.id)

            startActivity(intent)
        }
    }

}