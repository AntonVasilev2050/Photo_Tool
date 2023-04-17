package com.avv2050soft.unsplashtool.presentation.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentSecondScreenBinding

class SecondScreen : Fragment(R.layout.fragment_second_screen) {
    private val binding by viewBinding(FragmentSecondScreenBinding::bind)

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_second_screen, container, false)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.textViewNext.setOnClickListener {
            viewPager?.currentItem = 2
        }

//        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
//        binding.textViewNext.alpha = 0f
//        binding.textViewNext.animate().setDuration(4000).alpha(1f).withEndAction {
//            binding.textViewNext.setOnClickListener {
//                viewPager?.currentItem = 2
//            }
//        }
    }

}