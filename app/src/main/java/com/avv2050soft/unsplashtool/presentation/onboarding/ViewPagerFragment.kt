package com.avv2050soft.unsplashtool.presentation.onboarding

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.internal.findRootView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentViewPagerBinding
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.FirstScreen
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.ForthScreen
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.SecondScreen
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.ThirdScreen
import com.avv2050soft.unsplashtool.presentation.utils.hideAppbarAndBottomView

class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {

    private val binding by viewBinding(FragmentViewPagerBinding::bind)
    private val viewModel: ViewPagerViewModel by viewModels()
    private lateinit var indicatorsContainer: LinearLayout
    private var adapter:ViewPagerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setFragmentManager(childFragmentManager)
        hideAppbarAndBottomView(requireActivity())
        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen(),
            ForthScreen()
        )

        adapter = viewModel.getFragmentManager()?.let {
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
            textViewSkip.setOnClickListener {
                findNavController().navigate(R.id.loginFragment)
            }
        }
    }

    override fun onDestroy() {
//        binding.viewPager.unregisterOnPageChangeCallback()
        super.onDestroy()
    }

    override fun onDestroyView() {

        super.onDestroyView()
    }

    private fun setupIndicators(){
        val mainActivity = requireActivity()
        indicatorsContainer = mainActivity.findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(adapter?.itemCount ?: 4)
    }
}