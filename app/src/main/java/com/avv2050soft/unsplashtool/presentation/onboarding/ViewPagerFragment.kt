package com.avv2050soft.unsplashtool.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.FirstScreen
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.ForthScreen
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.SecondScreen
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.ThirdScreen

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen(),
            ForthScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = adapter
        return view
    }
}