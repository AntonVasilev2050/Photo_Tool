package com.avv2050soft.unsplashtool.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avv2050soft.unsplashtool.domain.models.photo_details.PhotoDetails
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository
) : ViewModel() {

    private var photoDetails: PhotoDetails? = null
    private val _photoDetailsStateFlow = MutableStateFlow(photoDetails)
    val photoDetailsStateFlow = _photoDetailsStateFlow

    fun getPhotoDetails(id: String){
        viewModelScope.launch {
            try {
                photoDetails = unsplashRepository.getPhotoDetails(id)
                _photoDetailsStateFlow.value = photoDetails
            } catch (e : Exception){
                Log.d("data_test", "Error in VM: ${e.message.toString()}")
            }
        }
    }
}