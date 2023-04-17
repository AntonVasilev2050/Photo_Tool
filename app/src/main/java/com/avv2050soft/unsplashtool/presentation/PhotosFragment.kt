package com.avv2050soft.unsplashtool.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import com.avv2050soft.unsplashtool.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class PhotosFragment : Fragment() {

    private lateinit var viewModel: PhotosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = requireActivity()
        mainActivity.findViewById<Toolbar>(R.id.toolbar).isGone = false
        val bottomNavigationView = mainActivity.findViewById<BottomNavigationView>(R.id.bottomView)
        bottomNavigationView.isGone = false
    }

}