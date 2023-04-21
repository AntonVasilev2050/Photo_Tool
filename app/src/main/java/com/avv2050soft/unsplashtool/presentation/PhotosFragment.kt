package com.avv2050soft.unsplashtool.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentPhotosBinding
import com.avv2050soft.unsplashtool.domain.models.photos.Photo
import com.avv2050soft.unsplashtool.presentation.adapters.PhotosAdapter
import com.avv2050soft.unsplashtool.presentation.adapters.PhotosLoadStateAdapter
import com.avv2050soft.unsplashtool.presentation.utils.showAppbarAndBottomView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

const val PHOTO_ID_KEY = "photo_id_key"

@AndroidEntryPoint
class PhotosFragment : Fragment(R.layout.fragment_photos) {

    private val binding by viewBinding(FragmentPhotosBinding::bind)
    private val viewModel: PhotosViewModel by viewModels()
    private val photoAdapter = PhotosAdapter { photo: Photo -> onItemClick(photo) }

    private fun onItemClick(photo: Photo) {
        val photoIdBundle = Bundle()
        photoIdBundle.putString(PHOTO_ID_KEY, photo.id)
        findNavController().navigate(R.id.action_photosFragment_to_photoDetailsFragment, photoIdBundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAppbarAndBottomView(requireActivity())
//        binding.recyclerViewPhotos.setHasFixedSize(true)
        binding.recyclerViewPhotos.adapter =
            photoAdapter.withLoadStateFooter(PhotosLoadStateAdapter())

        binding.swipeRefresh.setOnRefreshListener { photoAdapter.refresh() }

        photoAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.pagePhotos.onEach {
            photoAdapter.submitData(it)
            if (photoAdapter.snapshot().items.isEmpty()) {
                viewModel.loadAllPhotosFromDb()
                viewModel.photosFromDbStateFlow.collect { listOfPhotos ->
                    photoAdapter.submitData(PagingData.from(listOfPhotos))
                    Log.d("data_test", "photoListPageDB - ${listOfPhotos.toString()}")
                    Toast.makeText(requireContext(), "Loaded from database", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        photoAdapter.addOnPagesUpdatedListener {
            lifecycleScope.launch {
                val photoListPage = photoAdapter.snapshot().items
                photoListPage.forEach {
                    viewModel.insertPhotoInDatabase(it)
                }
            }
        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.CREATED){
//                viewModel.loadAllPhotosFromDb()
//                viewModel.photosFromDbStateFlow.collect{
//                    photoAdapter.submitData(PagingData.from(it))
//                    Log.d("data_test", "photoListPageDB - ${it.toString()}" )
//                }
//            }
//        }

    }
}