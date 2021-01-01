package com.example.filmapp.home.melhores

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.agenda.AssistirMaisTardeViewModel
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeEntity
import com.example.filmapp.home.descubra.DescubraSeriesAdapter
import kotlinx.android.synthetic.main.fragment_melhores_series.*
import kotlinx.android.synthetic.main.fragment_melhores_series.view.*

class MelhoresSeriesFragment : Fragment(), MelhoresSeriesAdapter.onMelhoresSerieClickListener {

    private lateinit var melhoresSeriesAdapter: MelhoresSeriesAdapter
    private lateinit var melhoresSeriesLayoutManager: LinearLayoutManager
    private lateinit var viewModelAssistirMaisTarde: AssistirMaisTardeViewModel
    lateinit var listDataBase: List<AssistirMaisTardeEntity>
    var page = 1
    var totalPages = 3

    val viewModel by viewModels<MelhoresSeriesViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MelhoresSeriesViewModel(service) as T
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
        val view = inflater.inflate(R.layout.fragment_melhores_series, container, false)

        viewModelAssistirMaisTarde = ViewModelProvider(this).get(AssistirMaisTardeViewModel::class.java)

        //Iniciando o ReciclerView de Melhores Séries
        melhoresSeriesLayoutManager = LinearLayoutManager(context)
        melhoresSeriesAdapter = MelhoresSeriesAdapter(this)
        view.rc_melhoresSeriesList.layoutManager = melhoresSeriesLayoutManager
        view.rc_melhoresSeriesList.adapter = melhoresSeriesAdapter
        view.rc_melhoresSeriesList.isHorizontalFadingEdgeEnabled
        view.rc_melhoresSeriesList.setHasFixedSize(true)

        viewModelAssistirMaisTarde.mediaList.observe(viewLifecycleOwner){
            listDataBase = it
        }

        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner) {
            var mediaList = viewModelAssistirMaisTarde.checkTVInList(it.results, listDataBase)
            melhoresSeriesAdapter.addList(mediaList)
        }

        viewModel.getTopSeriesList()

        return view
    }

    override fun melhoresItemClick(position: Int) {
        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner) {
            var mediaList = it.results
            var media = mediaList.get(position)

            val intent = Intent(context, MediaSelectedActivity::class.java)
            intent.putExtra("poster", "https://image.tmdb.org/t/p/w500" + media.poster_path)
            intent.putExtra("movie", false)
            intent.putExtra("mediaSerie", media)

            startActivity(intent)
        }
    }

    override fun saveInAssistirMaisTardeList(position: Int) {
        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            var media = mediaList.get(position)

            var newEntity = AssistirMaisTardeEntity(media.id, media.name, media.poster_path, "tv")
            viewModelAssistirMaisTarde.saveNewMedia(newEntity)
            Toast.makeText(context, "Série adicionada a Agenda", Toast.LENGTH_SHORT).show()
        }
    }

    override fun removeOfAssistirMaisTardeList(position: Int) {
        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner){
            var mediaList = it.results
            var media = mediaList.get(position)

            var removeEntity = AssistirMaisTardeEntity(media.id, media.name, media.poster_path, "tv")
            viewModelAssistirMaisTarde.removeMedia(removeEntity)
            Toast.makeText(context, "Série removida da Agenda", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setScrollView(view: View) {
        view.rc_melhoresSeriesList.run {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {

                        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner) {

                            var itensVisible = melhoresSeriesLayoutManager?.childCount
                            var pastItens = melhoresSeriesLayoutManager.findFirstVisibleItemPosition()
                            var totalItens = melhoresSeriesAdapter.itemCount
                            totalPages = it.total_pages

//                            Log.i("itensVisible", itensVisible.toString())
//                            Log.i("pastItens", pastItens.toString())
                            Log.i("totalItens", totalItens.toString())

                            if (((itensVisible + pastItens) == totalItens) && (page < totalPages)) {
                                page++
                                Log.i("CHAMOU A PAGINA", page.toString())
                                viewModel.getTopSeriesList()
                            }
                        }

                    }

                }
            })
        }
    }

    private fun setScrollViewYouTube(view: View) {
        view.rc_melhoresSeriesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                var itensVisible = melhoresSeriesLayoutManager.childCount
                var pastItens = melhoresSeriesLayoutManager.findFirstCompletelyVisibleItemPosition()
                var totalItens = melhoresSeriesAdapter.itemCount

                Log.i("itensVisible2", itensVisible.toString())
                Log.i("pastItens2", pastItens.toString())
                Log.i("totalItens2", totalItens.toString())

                if ((itensVisible + pastItens) >= totalItens) {
                    page++
                    Log.i("ENTROU NO IF", "CHAMOU A PAGINA ${page}")
                }


                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

}