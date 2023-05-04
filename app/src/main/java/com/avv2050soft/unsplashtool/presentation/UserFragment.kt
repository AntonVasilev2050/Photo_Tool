package com.avv2050soft.unsplashtool.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentUserBinding
import com.avv2050soft.unsplashtool.domain.models.userinfo.CurrentUserInfo
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_user) {
    private val binding by viewBinding(FragmentUserBinding::bind)
    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.getCurrentUserInfo()
                viewModel.userInfoStateFlow.collect{
                    UserViewModel.username = it?.username.toString()
                    showUserInfo(it)
                }
            }
        }
    }

    private fun showUserInfo(currentUserInfo: CurrentUserInfo?) {
        currentUserInfo?.let {
            with(binding){
                Glide
                    .with(imageViewUserAvatar.context)
                    .load(it.profileImage.medium)
                    .into(imageViewUserAvatar)
                textViewUserProfileName.text = it.name
                textViewUserProfileUserName.text = it.username
                textViewUserProfileDescription.text = it.bio ?: "No data"
                textViewUserProfileLocation.text = it.location ?: "universe"
                textViewUserProfileEmail.text= it.email
                textViewUserProfileDownloads.text = it.downloads.toString()
            }
        }
    }

}