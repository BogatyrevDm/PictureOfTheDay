package com.example.pictureoftheday.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.pictureoftheday.R
import com.example.pictureoftheday.model.Days
import com.example.pictureoftheday.utils.getStringDateFromEnum

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragments = arrayOf(
        PictureOfTheDayFragment.newInstance(Days.DAYBEFOREYESTERDAY),
        PictureOfTheDayFragment.newInstance(Days.YESTERDAY),
        PictureOfTheDayFragment.newInstance(Days.TODAY)
    )

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[0]
            1 -> fragments[1]
            2 -> fragments[2]
            else -> fragments[2]
        }
    }


}