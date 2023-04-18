package com.avv2050soft.unsplashtool.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentViewPagerBinding
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.FirstScreen
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.ForthScreen
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.SecondScreen
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.ThirdScreen
import com.avv2050soft.unsplashtool.presentation.utils.hideAppbarAndBottomView

class ViewPagerFragment : Fragment() {

    private val binding by viewBinding(FragmentViewPagerBinding::bind)
    private val viewModel: ViewPagerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        viewModel.setFragmentManager(childFragmentManager)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideAppbarAndBottomView(requireActivity())
        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen(),
            ForthScreen()
        )

        val adapter = viewModel.getFragmentManager()?.let {
            ViewPagerAdapter(
                fragmentList,
                it,
                lifecycle
            )
        }

        binding.apply {
            viewPager.adapter = adapter
            textViewNextFinish.setOnClickListener {
                when (viewPager.currentItem) {
                    in (0 until fragmentList.lastIndex) -> viewPager.currentItem++

                    else -> findNavController().navigate(R.id.loginFragment)
                }
            }
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    when (position) {
                        in 0 until fragmentList.lastIndex -> {
                            textViewNextFinish.text = resources.getText(R.string.next)
                        }

                        else -> textViewNextFinish.text = resources.getText(R.string.finish)
                    }
                }
            })
        }


    }

    override fun onDestroy() {
        super.onDestroy()
    }
}