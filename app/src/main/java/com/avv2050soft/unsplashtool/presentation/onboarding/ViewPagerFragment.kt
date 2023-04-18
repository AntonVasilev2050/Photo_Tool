package com.avv2050soft.unsplashtool.presentation.onboarding

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentViewPagerBinding
import com.avv2050soft.unsplashtool.presentation.ONBOARDING_FINISHED_KEY
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.FirstScreen
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.ForthScreen
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.SecondScreen
import com.avv2050soft.unsplashtool.presentation.onboarding.screens.ThirdScreen
import com.avv2050soft.unsplashtool.presentation.utils.hideAppbarAndBottomView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {

    private val binding by viewBinding(FragmentViewPagerBinding::bind)
    private val viewModel: ViewPagerViewModel by viewModels()
    private lateinit var indicatorsContainer: LinearLayout
    private var adapter: ViewPagerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setFragmentManager(childFragmentManager)
        hideAppbarAndBottomView(requireActivity())
        setupIndicators()
        setCurrentIndicator(0)

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

                    else -> {
                        viewModel.saveIsOnboardingFinish(ONBOARDING_FINISHED_KEY, true)
                        findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
                    }
                }
            }
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    setCurrentIndicator(position)
                    when (position) {
                        in 0 until fragmentList.lastIndex -> {
                            textViewNextFinish.text = resources.getText(R.string.next)
                        }

                        else -> textViewNextFinish.text = resources.getText(R.string.finish)
                    }
                }
            })
            (viewPager.getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER

            textViewSkip.setOnClickListener {
                viewModel.saveIsOnboardingFinish(ONBOARDING_FINISHED_KEY, true)
                findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
            }
        }
    }

    private fun setupIndicators() {
        val mainActivity = requireActivity()
        indicatorsContainer = mainActivity.findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(adapter?.itemCount ?: 4)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(mainActivity.applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        mainActivity.applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val mainActivity = requireActivity()
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        mainActivity.applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        mainActivity.applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
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

}