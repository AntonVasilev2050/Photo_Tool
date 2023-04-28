package com.avv2050soft.unsplashtool.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentCollectionsBinding
import com.avv2050soft.unsplashtool.domain.models.collections.CollectionsItem
import com.avv2050soft.unsplashtool.presentation.adapters.CollectionsAdapter
import com.avv2050soft.unsplashtool.presentation.adapters.CommonLoadStateAdapter
import com.avv2050soft.unsplashtool.presentation.utils.showAppbarAndBottomView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

const val COLLECTION_ID_KEY = "collection_id_key"

@AndroidEntryPoint
class CollectionsFragment : Fragment(R.layout.fragment_collections) {
    private val binding by viewBinding(FragmentCollectionsBinding::bind)
    private val viewModel: CollectionsViewModel by viewModels()
    private val collectionsAdapter =
        CollectionsAdapter { collectionsItem: CollectionsItem -> onItemClick(collectionsItem) }

    private fun onItemClick(collectionsItem: CollectionsItem) {
        val collectionIdBundle = Bundle()
        collectionIdBundle.putString(COLLECTION_ID_KEY, collectionsItem.id)
        findNavController().navigate(
            R.id.action_collectionsFragment_to_collectionDetailsFragment,
            collectionIdBundle
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAppbarAndBottomView(requireActivity())
       binding.recyclerViewCollections .adapter = collectionsAdapter.withLoadStateFooter(CommonLoadStateAdapter())

        binding.swipeRefreshCollections .setOnRefreshListener { collectionsAdapter.refresh() }

        collectionsAdapter.loadStateFlow.onEach {
            binding.swipeRefreshCollections.isRefreshing = it.refresh == LoadState.Loading
        }

        viewModel.pageCollections.onEach {
            collectionsAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}