package com.avv2050soft.unsplashtool.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentCollectionDetailsBinding
import com.avv2050soft.unsplashtool.domain.models.collectioninfo.CollectionInfo
import com.avv2050soft.unsplashtool.domain.models.collectionphotos.CollectionPhotoItem
import com.avv2050soft.unsplashtool.presentation.adapters.CollectionPhotosAdapter
import com.avv2050soft.unsplashtool.presentation.adapters.CommonLoadStateAdapter
import com.avv2050soft.unsplashtool.presentation.utils.hideAppbarAndBottomView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CollectionDetailsFragment : Fragment(R.layout.fragment_collection_details) {
    private val binding by viewBinding(FragmentCollectionDetailsBinding::bind)
    private val viewModel: CollectionDetailsViewModel by viewModels()
    private val collectionPhotosAdapter =
        CollectionPhotosAdapter { collectionPhotoItem -> onItemClick(collectionPhotoItem) }

    private fun onItemClick(collectionPhotoItem: CollectionPhotoItem) {
        val collectionPhotoIdBundle = Bundle()
        collectionPhotoIdBundle.putString(PHOTO_ID_KEY, collectionPhotoItem.id)
        findNavController().navigate(
            R.id.action_collectionDetailsFragment_to_photoDetailsFragment,
            collectionPhotoIdBundle
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val collectionId = arguments?.getString(COLLECTION_ID_KEY)
        if (collectionId != null) {
            CollectionDetailsViewModel.collectionId = collectionId
        }
        hideAppbarAndBottomView(requireActivity())

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                collectionId?.let {
                    viewModel.getThisCollectionInfo(collectionId)
                    viewModel.collectionInfoStateFlow.collect { collectionInfo ->
                        showCollectionInfo(collectionInfo)
                    }
                }
            }
        }

        binding.recyclerViewCollectionPhotos.adapter =
            collectionPhotosAdapter.withLoadStateFooter(CommonLoadStateAdapter())

        binding.swipeRefreshCollectionPhotos.setOnRefreshListener { collectionPhotosAdapter.refresh() }

        collectionPhotosAdapter.loadStateFlow.onEach {
            binding.swipeRefreshCollectionPhotos.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.thisCollectionPhotos.onEach {
            collectionPhotosAdapter.submitData(it)
//            collectionId?.let {
//                viewModel.getThisCollectionInfo(collectionId)
//                viewModel.collectionInfoStateFlow.collect {collectionInfo ->
//                    showCollectionInfo(collectionInfo)
//                }
//            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showCollectionInfo(collectionInfo: CollectionInfo?) {
        collectionInfo?.let {
            with(binding) {
//                Glide
//                    .with(constraintLayoutCollectionDetails.context)
//                    .load(it.coverPhoto.urls.regular)
//                    .into(constraintLayoutCollectionDetails.background)
                textViewCollectionDetailsTitle.text = it.title
                val tagTitlesList = mutableListOf<String>()
                it.tags.forEach { tag -> tagTitlesList.add(tag.title) }
                if (tagTitlesList.isNotEmpty()) {
                    textViewCollectionDetailsTags.text = buildString {
                        append("#")
                        append(tagTitlesList.joinToString(" #"))
                    }
                }
                textViewCollectionDetailsDescription.text = it.description
                textViewCollectionDetailsTotalPhotosCount.text = it.totalPhotos.toString()
                textViewCollectionDetailsUserName.text = it.user.username
            }
        }
    }
}