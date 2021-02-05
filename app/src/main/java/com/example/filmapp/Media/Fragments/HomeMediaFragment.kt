package com.example.filmapp.Media.Fragments

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Media.Adapters.FavoritosAdapterMovie
import com.example.filmapp.Media.Adapters.FavoritosAdapterSerie
import com.example.filmapp.Media.Adapters.HomeMediaMovieAdapter
import com.example.filmapp.Media.Adapters.HomeMediaSerieAdapter
import com.example.filmapp.Media.Models.FavoritosViewModel
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.Media.dataBase.FavoritoScope
import com.example.filmapp.Media.dataBase.FavoritosEntity
import com.example.filmapp.R
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.main.custom_alert.view.*
import kotlinx.android.synthetic.main.fragment_home_media.*
import kotlinx.android.synthetic.main.fragment_home_media.view.*


class HomeMediaFragment() : Fragment(), HomeMediaMovieAdapter.OnHomeMediaMovieClickListener,
    HomeMediaSerieAdapter.OnHomeMediaSerieClickListener,
    FavoritosAdapterMovie.FavoritosItemClickListener,
    FavoritosAdapterSerie.FavoritosItemClickListener {
    private lateinit var MovieAdapter: HomeMediaMovieAdapter
    private lateinit var SerieAdapter: HomeMediaSerieAdapter
    private lateinit var MovieFavAdapter: FavoritosAdapterMovie
    private lateinit var SerieFavAdapter: FavoritosAdapterSerie
    private lateinit var lManager: LinearLayoutManager
    private lateinit var viewModelFav: FavoritosViewModel
    lateinit var ListMediaMovie: ArrayList<ResultMovie>
    lateinit var ListMediaSerie: ArrayList<ResultTv>
    var ListMediaMovieFav = ArrayList<FavoritoScope>()
    var ListMediaSerieFav = ArrayList<FavoritoScope>()
    var page = 1


    var Movie: Boolean? = null
    var config = Config()


    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(service) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            Movie = arguments?.getBoolean(movie)
        }
    }

    companion object {
        private val movie = "movie"

        fun newInstance(Movie: Boolean): HomeMediaFragment {
            val fragment = HomeMediaFragment()
            val args = Bundle()
            args.putBoolean(movie, Movie)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_home_media, container, false)

        if(testConnection() == true) {
            viewModel.getFavoritoist()

            if (Movie == true) {
                viewModel.config.observe(viewLifecycleOwner) {
                    config = it
                }
                viewModel.getConfig()
                viewModel.listResMovies.observe(viewLifecycleOwner) {
                    ListMediaMovie = it.results
                    MovieAdapter = HomeMediaMovieAdapter(ListMediaMovie, this, Movie, config)
                    lManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    view.rv_pop.layoutManager = lManager
                    view.rv_pop.adapter = MovieAdapter
                    view.rv_pop.setHasFixedSize(true)
                }
                viewModel.getPopularMovies(page)
                setScrollViewFilmes(view.rv_pop)
                viewModel.returnFavoritoList.observe(viewLifecycleOwner) {
                    it.forEach {
                        if (it.type == "Movie") {
                            ListMediaMovieFav.add(it)
                        }
                    }
                    MovieFavAdapter = FavoritosAdapterMovie(this)
                    lManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    MovieFavAdapter.addList(ListMediaMovieFav)
                    ListMediaMovieFav = ArrayList()
                    view.rv_fav.layoutManager = lManager
                    view.rv_fav.adapter = MovieFavAdapter
                    view.rv_fav.setHasFixedSize(true)
                }

            } else {
                viewModel.config.observe(viewLifecycleOwner) {
                    config = it
                    viewModel.getPopularSeries(page)
                }
                viewModel.getConfig()
                viewModel.listResSeries.observe(viewLifecycleOwner) {
                    ListMediaSerie = it.results
                    SerieAdapter = HomeMediaSerieAdapter(ListMediaSerie, this, Movie, config)
                    lManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    view.rv_pop.layoutManager = lManager
                    view.rv_pop.adapter = SerieAdapter
                    view.rv_pop.setHasFixedSize(true)
                }
                setScrollViewSeries(view.rv_pop)
                viewModel.returnFavoritoList.observe(viewLifecycleOwner) {
                    it.forEach {
                        if (it.type == "Tv") {
                            ListMediaSerieFav.add(it)
                        }
                    }
                    SerieFavAdapter = FavoritosAdapterSerie(this)
                    lManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    SerieFavAdapter.addList(ListMediaSerieFav)
                    ListMediaSerieFav = ArrayList()
                    view.rv_fav.layoutManager = lManager
                    view.rv_fav.adapter = SerieFavAdapter
                    view.rv_fav.setHasFixedSize(true)
                }
            }
        }
        return view
    }


    override fun homeMediaMovieClick(position: Int) {
        if (Movie == true) {
            val media = ListMediaMovie.get(position)
            val intent = Intent(context, MediaSelectedActivity::class.java)
            intent.putExtra("poster", media.poster_path)
            intent.putExtra("movie", Movie)
            intent.putExtra("sinopse", media.overview)
            intent.putExtra("id", media.id)
            startActivity(intent)
            MovieAdapter.notifyDataSetChanged()
        }


    }

    override fun homeMediaSerieClick(position: Int) {
        if (Movie == false) {
            val media = ListMediaSerie.get(position)
            val intent = Intent(context, MediaSelectedActivity::class.java)
            intent.putExtra("poster", media.poster_path)
            intent.putExtra("movie", Movie)
            intent.putExtra("sinopse", media.overview)
            intent.putExtra("id", media.id)
            startActivity(intent)
            SerieAdapter.notifyDataSetChanged()
        }
    }

    override fun favoritosItemClick(position: Int) {
        viewModelFav.mediaList.observe(viewLifecycleOwner) {
            val media = it.get(position)
        }
    }

    override fun favoritosLongClickSerie(position: Int) {
        val movieFav = ListMediaMovieFav.get(position)
        // creatAlert(movieFav)
        // updateListSerieFav()
    }

    override fun favoritosLongClick(position: Int) {
        val movieFav = ListMediaMovieFav.get(position)
        //  creatAlert(movieFav)
        //  updateListFav()
    }

    fun creatAlert(movieFav: FavoritoScope) {
        val builder = AlertDialog.Builder(requireActivity()).create()
        val view: View = LayoutInflater.from(requireActivity()).inflate(R.layout.custom_alert, null)
        builder.setView(view)
        builder.show()
        view.btAlert_confirm.setOnClickListener {
            Toast.makeText(requireActivity(), "Item Removido", Toast.LENGTH_SHORT).show()
            viewModel.deleteFromFavoritoList(movieFav)
            builder.dismiss()
        }
        view.btAlert_Notconfirm.setOnClickListener {
            Toast.makeText(requireActivity(), "O coração continua unido", Toast.LENGTH_SHORT).show()
            builder.dismiss()
        }

    }

//    fun updateListFav(){
//        viewModel.returnFavoritoList.observe(viewLifecycleOwner) {
//            it.forEach {
//                if (it.type == "Movie"){
//                    ListMediaMovieFav.add(it)
//                }else{
//                    ListMediaSerieFav.add(it)
//                }
//            }
//        }
//    }
//    fun updateListSerieFav(){
//        viewModel.returnFavoritoList.observe(viewLifecycleOwner) {
//            it.forEach {
//                if (it.type == "Tv"){
//                    ListMediaSerieFav.add(it)
//                }else{
//                    ListMediaMovieFav.add(it)
//                }
//            }
//        }
//    }

    private fun setScrollViewFilmes(rv_main: RecyclerView) {
        rv_main.run {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    //somente atualiza quando parar de scrollar
                    if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                        val visibleItem = lManager?.childCount
                        val unloadedItem = lManager.findFirstVisibleItemPosition()
                        val total = adapter?.itemCount
                        if ((visibleItem + unloadedItem) == total) {
                            page = page + 1
                            viewModel.getPopularMovies(page)
                        }
                    }
                }
            })
        }
    }

    private fun setScrollViewSeries(rv_pop: RecyclerView) {
        rv_pop.run {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    //somente atualiza quando parar de scrollar
                    if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                        val visibleItem = lManager?.childCount
                        val unloadedItem = lManager.findFirstVisibleItemPosition()
                        if ((visibleItem + unloadedItem) == 20) {
                            page = page + 1
                            viewModel.getPopularSeries(page)
                        }
                    }
                }
            })
        }
    }



    fun testConnection(): Boolean {
        val cm = activity?.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected

    }

}
