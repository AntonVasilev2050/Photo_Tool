package com.avv2050soft.unsplashtool.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.room.util.newStringBuilder
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentUserBinding
import com.avv2050soft.unsplashtool.domain.models.userinfo.CurrentUserInfo
import com.avv2050soft.unsplashtool.domain.models.userlikedphotos.UserLikedPhotoItem
import com.avv2050soft.unsplashtool.presentation.adapters.CommonLoadStateAdapter
import com.avv2050soft.unsplashtool.presentation.adapters.UserLikedPhotosAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_user) {
    private val binding by viewBinding(FragmentUserBinding::bind)
    private val userViewModel: UserViewModel by viewModels()
    private val userLikedPhotosAdapter =
        UserLikedPhotosAdapter { userLikedPhotoItem: UserLikedPhotoItem ->
            onItemClick(userLikedPhotoItem)
        }

    private fun onItemClick(userLikedPhotoItem: UserLikedPhotoItem) {
        val photoIdBundle = Bundle()
        photoIdBundle.putString(PHOTO_ID_KEY, userLikedPhotoItem.id)
        findNavController().navigate(
            R.id.action_userFragment_to_photoDetailsFragment,
            photoIdBundle
        )
    }

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

        binding.recyclerViewUserLikedPhotos.adapter =
            userLikedPhotosAdapter.withLoadStateFooter(CommonLoadStateAdapter())

        binding.swipeRefresh.setOnRefreshListener { userLikedPhotosAdapter.refresh() }

        userLikedPhotosAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                userViewModel.getCurrentUserInfo()
                userViewModel.userInfoStateFlow.collect {
                    UserViewModel.username = it?.username.toString()
                    showUserInfo(it)
                }
            }
        }

        binding.tabLayoutUser.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.recyclerViewUserLikedPhotos.isGone = true
                    }

                    1 -> {
                        binding.recyclerViewUserLikedPhotos.isGone = false
                    }

                    2 -> {
                        binding.recyclerViewUserLikedPhotos.isGone = true
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
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

                tabLayoutUser.getTabAt(0)?.text = newStringBuilder()
                    .append(getString(R.string.photos_tab))
                    .append("(${it.totalPhotos})")
                tabLayoutUser.getTabAt(1)?.text = newStringBuilder()
                    .append(getString(R.string.liked_tab))
                    .append("(${it.totalLikes})")
                tabLayoutUser.getTabAt(2)?.text = newStringBuilder()
                    .append(getString(R.string.collections_tab))
                    .append("(${it.totalCollections})")
            }
            userViewModel.userLikedPhotos.onEach {
                userLikedPhotosAdapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }
}