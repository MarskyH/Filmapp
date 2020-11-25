package com.example.filmapp.Media.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Classes.Media
import com.example.filmapp.Media.Adapters.HomeMediaAdapter
import com.example.filmapp.R
import kotlinx.android.synthetic.main.fragment_home_media.view.*


class HomeMediaFragment(val ListMedia:ArrayList<Media>): Fragment(), HomeMediaAdapter.OnHomeMediaClickListener {

    var listaMedias = getMediaList()
    var adapter = HomeMediaAdapter(listaMedias, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_home_media, container, false)
        view.rv_fav.adapter = adapter
        view.rv_pop.adapter = adapter
        view.rv_fav.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.rv_pop.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        view.rv_pop.setHasFixedSize(true)
        view.rv_fav.setHasFixedSize(true)
        return view
    }

    fun getMediaList(): ArrayList<Media>{

        return ListMedia

    }


    override fun homeMediaClick(position: Int) {
        val media = listaMedias.get(position)
        adapter.notifyItemChanged(position)
    }


}
