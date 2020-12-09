package com.example.filmapp.Home.Adapters.ViewPagers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.filmapp.Classes.Media

class ViewPagerHomeAdapter(supportFragmentManager: FragmentManager): FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentsList = ArrayList<Fragment>()
    private val fragmentsNamesList = ArrayList<String>()
//teste
    override fun getCount(): Int {
        return fragmentsList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentsList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentsNamesList[position]
    }

    fun addFragment(newFragment: Fragment, nameFragment: String){
        fragmentsList.add(newFragment)
        fragmentsNamesList.add(nameFragment)
    }





}