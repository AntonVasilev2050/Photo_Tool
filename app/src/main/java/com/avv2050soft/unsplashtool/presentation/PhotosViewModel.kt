package com.avv2050soft.unsplashtool.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.avv2050soft.unsplashtool.data.PhotoPagingSource
import com.avv2050soft.unsplashtool.data.api.UnsplashApi.Companion.PER_PAGE
import com.avv2050soft.unsplashtool.domain.models.photos.Photo
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val repository: UnsplashRepository
) : ViewModel() {
    val pagePhotos: Flow<PagingData<Photo>> = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = { PhotoPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)
}