package com.avv2050soft.unsplashtool.presentation.utils

import android.app.Activity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import com.avv2050soft.unsplashtool.R
import com.google.android.material.bottomnavigation.BottomNavigationView

fun hideAppbarAndBottomView(requireActivity: Activity) {
    requireActivity.findViewById<Toolbar>(R.id.toolbar).isGone = true
    val bottomNavigationView = requireActivity.findViewById<BottomNavigationView>(R.id.bottomView)
    bottomNavigationView.isGone = true
}

fun showAppbarAndBottomView(requireActivity: Activity) {
    requireActivity.findViewById<Toolbar>(R.id.toolbar).isGone = false
    val bottomNavigationView = requireActivity.findViewById<BottomNavigationView>(R.id.bottomView)
    bottomNavigationView.isGone = false
}