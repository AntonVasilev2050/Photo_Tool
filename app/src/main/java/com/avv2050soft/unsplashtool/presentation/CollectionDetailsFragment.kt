package com.avv2050soft.unsplashtool.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentCollectionDetailsBinding
import com.avv2050soft.unsplashtool.domain.models.collectioninfo.CollectionInfo
import com.avv2050soft.unsplashtool.domain.models.photo_details.PhotoDetails
import com.avv2050soft.unsplashtool.presentation.utils.hideAppbarAndBottomView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CollectionDetailsFragment : Fragment(R.layout.fragment_collection_details) {
    private val binding by viewBinding(FragmentCollectionDetailsBinding::bind)
    private val viewModel: CollectionDetailsViewModel by viewModels()

    private var collectionInfo: CollectionInfo? = null
//
//    private val requestDownloadPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (isGranted) {
//                photoDetails?.let { downloadPhoto(it) }
//            } else {
//                Snackbar.make(
//                    requireView().rootView,
//                    getString(R.string.permission_is_required_to_download_and_save_the_file),
//                    Snackbar.LENGTH_LONG
//                )
//                    .setAction(getString(R.string.grant_permission)) {
//                        photoDetails?.let { it1 -> checkDownloadPermission(it1) }
//                    }
//                    .show()
//            }
//        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val collectionId = arguments?.getString(COLLECTION_ID_KEY)
        if (collectionId != null) {
            CollectionDetailsViewModel.collectionId = collectionId
        }
        hideAppbarAndBottomView(requireActivity())

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                collectionId?.let {
                    viewModel.getThisCollectionInfo(collectionId)
                    viewModel.collectionInfoStateFlow.collect {
                        collectionInfo = it
                        showCollectionInfo(collectionInfo)
                    }
                }
            }
        }
    }

    private fun showCollectionInfo(collectionInfo: CollectionInfo?) {
        collectionInfo?.let {
            with(binding){

            }
        }
    }

}