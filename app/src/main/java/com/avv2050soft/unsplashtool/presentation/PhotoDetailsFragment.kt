package com.avv2050soft.unsplashtool.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentPhotoDetailsBinding
import com.avv2050soft.unsplashtool.domain.models.photo_details.PhotoDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotoDetailsFragment : Fragment(R.layout.fragment_photo_details) {
    private val binding by viewBinding(FragmentPhotoDetailsBinding::bind)
    private val viewModel: PhotoDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoId = arguments?.getString(PHOTO_ID_KEY)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                photoId?.let {
                    viewModel.getPhotoDetails(it)
                    viewModel.photoDetailsStateFlow.collect {
                        showPhotoDetails(it)
                    }
                }
            }
        }
    }

    private fun showPhotoDetails(photoDetails: PhotoDetails?) {
        Log.d("data_test", "Photo details: ${photoDetails.toString()}")
    }
}