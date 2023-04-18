package com.avv2050soft.unsplashtool.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentSplashBinding
import com.avv2050soft.unsplashtool.presentation.utils.hideAppbarAndBottomView
import dagger.hilt.android.AndroidEntryPoint

const val ONBOARDING_FINISHED_KEY = "onboarding_finished_key"

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val viewModel: SplashFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideAppbarAndBottomView(requireActivity())
        binding.imageViewSplashImage.alpha = 0f
        binding.imageViewSplashImage.animate().setDuration(1500).alpha(1f).withEndAction {

            if (viewModel.getIsOnboardingFinished(ONBOARDING_FINISHED_KEY, false)) {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }
        }
    }
}