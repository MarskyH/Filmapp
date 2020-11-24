package com.example.filmapp.Home.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.filmapp.R

class AjudaDetailsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_ajuda_details, container, false)

        return view
    }

    companion object {
        fun newInstance() = AjudaDetailsFragment()
    }
}