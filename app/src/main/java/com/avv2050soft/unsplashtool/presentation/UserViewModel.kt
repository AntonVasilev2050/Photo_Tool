package com.avv2050soft.unsplashtool.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.avv2050soft.unsplashtool.data.UserLikedPhotoItemPagingSource
import com.avv2050soft.unsplashtool.data.api.UnsplashApi.Companion.PER_PAGE
import com.avv2050soft.unsplashtool.domain.models.userinfo.CurrentUserInfo
import com.avv2050soft.unsplashtool.domain.models.userlikedphotos.UserLikedPhotoItem
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository
) : ViewModel() {
    private var userInfo: CurrentUserInfo? = null
    private val _userInfoStateFlow = MutableStateFlow(userInfo)
    val userInfoStateFlow = _userInfoStateFlow

    val userLikedPhotos: Flow<PagingData<UserLikedPhotoItem>> = Pager(
        config = PagingConfig(pageSize = PER_PAGE),
        pagingSourceFactory = {UserLikedPhotoItemPagingSource(unsplashRepository)}
    ).flow.cachedIn(viewModelScope)

    fun getCurrentUserInfo() {
        viewModelScope.launch {
            try {
                userInfo = unsplashRepository.getCurrentUserInfo()
                _userInfoStateFlow.value = userInfo
            } catch (e: Exception) {
                Log.d("data_test", "Error in VM: ${e.message.toString()}")
            }
        }
    }

    companion object{
        var username : String = ""
    }
}