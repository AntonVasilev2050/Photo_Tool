package com.avv2050soft.unsplashtool.presentation

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentLogoutBinding
import com.avv2050soft.unsplashtool.presentation.utils.hideAppbarAndBottomView
import com.avv2050soft.unsplashtool.presentation.utils.launchAndCollectIn
import com.avv2050soft.unsplashtool.presentation.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutFragment : Fragment(R.layout.fragment_logout) {

    private val viewModel: LogoutViewModel by viewModels()
    private val binding by viewBinding(FragmentLogoutBinding::bind)

    private val logoutResponse = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.webLogoutComplete()
        } else {

            viewModel.webLogoutComplete()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideAppbarAndBottomView(requireActivity())
        bindViewModel()
    }

    private fun bindViewModel() {

        viewModel.logout()

        viewModel.loadingFlow.launchAndCollectIn(viewLifecycleOwner) {
            updateIsLoading(it)
        }
        viewModel.toastFlow.launchAndCollectIn(viewLifecycleOwner) {
            toast(it)
        }
        viewModel.logoutPageFlow.launchAndCollectIn(viewLifecycleOwner) {
            logoutResponse.launch(it)
        }

        viewModel.logoutCompletedFlow.launchAndCollectIn(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_logoutFragment_to_splashFragment)
        }
    }

    private fun updateIsLoading(isLoading: Boolean) = with(binding) {
        loginProgress.isVisible = isLoading
    }
}