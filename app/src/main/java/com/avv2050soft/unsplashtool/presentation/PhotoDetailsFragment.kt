package com.avv2050soft.unsplashtool.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.FragmentPhotoDetailsBinding
import com.avv2050soft.unsplashtool.domain.models.photo_details.PhotoDetails
import com.avv2050soft.unsplashtool.presentation.utils.hideAppbarAndBottomView
import com.avv2050soft.unsplashtool.presentation.utils.showAppbarAndBottomView
import com.avv2050soft.unsplashtool.presentation.utils.toStringWithKNotation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotoDetailsFragment : Fragment(R.layout.fragment_photo_details) {
    private val binding by viewBinding(FragmentPhotoDetailsBinding::bind)
    private val viewModel: PhotoDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoId = arguments?.getString(PHOTO_ID_KEY)
        hideAppbarAndBottomView(requireActivity())

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
        photoDetails?.let {
            with(binding) {
                Glide
                    .with(imageViewPhoto.context)
                    .load(photoDetails.urls.regular)
                    .into(imageViewPhoto)
                Glide
                    .with(imageViewAvatar.context)
                    .load(photoDetails.user.profileImage.medium)
                    .transform(CircleCrop())
                    .into(imageViewAvatar)
                textViewAuthorName.text = photoDetails.user.name
                textViewUserName.text = photoDetails.user.username
                val totalLikes = photoDetails.likes.toStringWithKNotation()
                textViewTotalLikeCount.text = totalLikes
                textViewLocation.text = photoDetails.user.location
                val tagTitlesList = mutableListOf<String>()
                photoDetails.tags.forEach { tag ->
                    tagTitlesList.add(tag.title)
                }
                textViewTags.text = buildString {
                    append("#")
                    append(tagTitlesList.joinToString(" #"))
                }
                textViewMade.text = photoDetails.exif.make
                textViewModel.text = photoDetails.exif.model
                textViewExposure.text = photoDetails.exif.exposureTime
                textViewAperture.text = photoDetails.exif.aperture
                textViewFocalLength.text = photoDetails.exif.focalLength
                textViewIso.text = photoDetails.exif.iso.toString()
                textViewDownloads.text = photoDetails.downloads.toStringWithKNotation()
                textViewAbout.text = photoDetails.user.bio
                textViewUserName2.text = photoDetails.user.username
            }
        }
    }

    override fun onDestroy() {
        showAppbarAndBottomView(requireActivity())
        super.onDestroy()
    }
}