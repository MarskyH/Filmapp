package com.example.filmapp.Media.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerMedia(supportFragmentManager: FragmentManager): FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()
    private val mFragmentImgList = ArrayList<Unit>()


    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence?{
        return mFragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
    fun addFragmentImage(fragment: Fragment, title: String, img: Int){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }


}