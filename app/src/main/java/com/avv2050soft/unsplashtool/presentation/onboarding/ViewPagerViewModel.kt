package com.avv2050soft.unsplashtool.presentation.onboarding

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel

class ViewPagerViewModel : ViewModel() {

    private var fragmentManager: FragmentManager? = null

    fun setFragmentManager(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    fun getFragmentManager(): FragmentManager? {
        return fragmentManager
    }

    override fun onCleared() {
        super.onCleared()
        fragmentManager = null
    }
}