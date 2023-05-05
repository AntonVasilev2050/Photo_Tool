package com.avv2050soft.unsplashtool.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
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
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_user) {
    private val binding by viewBinding(FragmentUserBinding::bind)
    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                val findItem = menu.findItem(R.id.action_search)
                val logoutItem = menu.findItem(R.id.action_logout)
                findItem.isVisible = false
                logoutItem.isVisible = true
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                userViewModel.getCurrentUserInfo()
                userViewModel.userInfoStateFlow.collect {
                    UserViewModel.username = it?.username.toString()
                    showUserInfo(it)
                }
            }
        }
    }

    private fun showUserInfo(currentUserInfo: CurrentUserInfo?) {
        currentUserInfo?.let {
            with(binding) {
                Glide
                    .with(imageViewUserAvatar.context)
                    .load(it.profileImage.medium)
                    .into(imageViewUserAvatar)
                textViewUserProfileName.text = it.name
                textViewUserProfileUserName.text = it.username
                textViewUserProfileDescription.text = it.bio ?: "No data"
                textViewUserProfileLocation.text = it.location ?: "universe"
                textViewUserProfileEmail.text = it.email
                textViewUserProfileDownloads.text = it.downloads.toString()
            }
        }
    }
}