package com.example.filmapp.Series.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filmapp.R


class MideaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_series_midea, container, false)
        return  view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
            arguments?.getInt("key")?.let {

        }
    }

    companion object{
        @JvmStatic
        fun newInstance(msg: Int) = MideaFragment().apply {

            }
        }
    }