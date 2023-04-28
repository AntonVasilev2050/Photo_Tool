package com.avv2050soft.unsplashtool.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentSearchPhotosBinding
import com.avv2050soft.unsplashtool.domain.models.photo_search.Result
import com.avv2050soft.unsplashtool.presentation.adapters.CommonLoadStateAdapter
import com.avv2050soft.unsplashtool.presentation.adapters.SearchPhotosAdapter
import com.avv2050soft.unsplashtool.presentation.utils.showAppbarAndBottomView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchPhotosFragment : Fragment(R.layout.fragment_search_photos) {

    private val binding by viewBinding(FragmentSearchPhotosBinding::bind)
    private val viewModel: SearchPhotosViewModel by viewModels()
    private val searchPhotosAdapter = SearchPhotosAdapter { result: Result -> onItemClick(result) }

    private fun onItemClick(result: Result) {
        val photoIdBundle = Bundle()
        photoIdBundle.putString(PHOTO_ID_KEY, result.id)
        findNavController().navigate(
            R.id.action_searchPhotosFragment_to_photoDetailsFragment,
            photoIdBundle
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAppbarAndBottomView(requireActivity())
        binding.recyclerViewSearchPhotos.adapter =
            searchPhotosAdapter.withLoadStateFooter(CommonLoadStateAdapter())

        binding.swipeRefresh.setOnRefreshListener { searchPhotosAdapter.refresh() }

        searchPhotosAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.searchPhotos.onEach {
            searchPhotosAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}