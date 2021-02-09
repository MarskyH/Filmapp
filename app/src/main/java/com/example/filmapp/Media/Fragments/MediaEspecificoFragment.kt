package com.example.filmapp.Media.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.Movie.SimilarMovies
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.Media.Adapters.MediaEspecificoMovieAdapter
import com.example.filmapp.Media.Adapters.MediaEspecificoSerieAdapter
import com.example.filmapp.Media.Models.EspecificoFragmentViewModel
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Series.Ui.SerieTemporadaActivity
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.custom_alert.view.*
import kotlinx.android.synthetic.main.fragment_series_seasons.view.*
import java.io.Serializable


class MediaEspecificoFragment() : Fragment(),
    MediaEspecificoSerieAdapter.OnMediaSerieClickListener,
    MediaEspecificoMovieAdapter.OnMediaMovieClickListener {
    private lateinit var SerieDetails: TvDetails
    private lateinit var listaSemelhantes: SimilarMovies
    lateinit var serieAdapter: MediaEspecificoSerieAdapter
    lateinit var movieAdapter: MediaEspecificoMovieAdapter
    var Movie: Boolean? = null
    var Id: String? = null
    var config = Config()


    private val viewModelEspecificoFragment by viewModels<EspecificoFragmentViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return EspecificoFragmentViewModel(service) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            Movie = arguments?.getBoolean(movie)
            Id = arguments?.getString(idMedia)
        }
    }

    companion object {
        private val movie = "movie"
        private val idMedia = "id"
        fun newInstance(Movie: Boolean, Id: String?): MediaEspecificoFragment {
            val fragment = MediaEspecificoFragment()
            val args = Bundle()
            args.putBoolean(movie, Movie)
            args.putString(idMedia, Id)
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_series_seasons, container, false)
        if (Movie == true) {
            viewModelEspecificoFragment.config.observe(viewLifecycleOwner) {
                try {
                    config = it
                } catch (e: Exception) {
                    creatAlertException(e)
                }

            }
            viewModelEspecificoFragment.getConfig()
            viewModelEspecificoFragment.listSimilar.observe(viewLifecycleOwner) {
                try {
                    listaSemelhantes = it
                    var adapter = MediaEspecificoMovieAdapter(listaSemelhantes, this, Movie, config)
                    view?.rv_temporada.adapter = adapter
                    view?.rv_temporada.layoutManager = GridLayoutManager(activity, 2)
                    view?.rv_temporada.setHasFixedSize(true)
                } catch (e: Exception) {
                    creatAlertException(e)
                }
            }
            viewModelEspecificoFragment.getSimilarMovies(Id!!)
        } else {

            viewModelEspecificoFragment.config.observe(viewLifecycleOwner) {
                try {
                    config = it
                } catch (e: Exception) {
                    creatAlertException(e)
                }
            }
            viewModelEspecificoFragment.getConfig()
            viewModelEspecificoFragment.listDetails.observe(viewLifecycleOwner) {
                try {
                    SerieDetails = it
                    val adapter = MediaEspecificoSerieAdapter(SerieDetails, this, config)
                    view?.rv_temporada.adapter = adapter
                    view?.rv_temporada.layoutManager = GridLayoutManager(activity, 2)
                    view?.rv_temporada.setHasFixedSize(true)
                } catch (e: Exception) {
                    creatAlertException(e)
                }

            }
            viewModelEspecificoFragment.getDetailsSerie(Id!!)
        }
        return view
    }

    override fun SeriemediaClick(position: Int) {
        try {
            val serie = SerieDetails
            val season = serie.seasons[position]
            val intent = Intent(context, SerieTemporadaActivity::class.java)
            intent.putExtra("serie", serie)
            intent.putExtra("season", season)
            intent.putExtra("poster_season", season.poster_path)
            startActivity(intent)
        } catch (e: Exception) {
            creatAlertException(e)
        }

    }

    override fun MoviemediaClick(position: Int) {
        try {
            val movie = listaSemelhantes.results.get(position)
            val intent = Intent(context, MediaSelectedActivity::class.java)
            intent.putExtra("poster", movie.poster_path)
            intent.putExtra("movie", true)
            intent.putExtra("id", movie.id)
            startActivity(intent)
        } catch (e: Exception) {
            creatAlertException(e)
        }

    }

    fun creatAlertException(e: Exception) {
        val user = FirebaseAuth.getInstance().currentUser
        val builder = AlertDialog.Builder(requireActivity()).create()
        val view: View = LayoutInflater.from(requireActivity()).inflate(R.layout.custom_alert_erro, null)
        builder.setView(view)
        builder.show()
        view.btAlert_confirm.setOnClickListener {
            val firebaseDB =
                FirebaseDatabase.getInstance().getReference().child("erros/${user?.uid}")
                    .setValue(e)
            Toast.makeText(
                activity,
                "Erro reportado, desculpe-nos pelo transtorno",
                Toast.LENGTH_SHORT
            ).show()
            builder.dismiss()
            getActivity()?.finish();
        }
        view.btAlert_Notconfirm.setOnClickListener {
            Toast.makeText(activity, "Erro ignorado", Toast.LENGTH_SHORT).show()
            builder.dismiss()
            getActivity()?.finish();

        }

    }
}
