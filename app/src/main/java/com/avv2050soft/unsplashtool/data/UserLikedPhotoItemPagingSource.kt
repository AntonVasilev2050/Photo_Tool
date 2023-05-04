package com.avv2050soft.unsplashtool.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.avv2050soft.unsplashtool.domain.models.userlikedphotos.UserLikedPhotoItem
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import com.avv2050soft.unsplashtool.presentation.UserViewModel
import javax.inject.Inject

class UserLikedPhotoItemPagingSource @Inject constructor(
    private val repository: UnsplashRepository
) : PagingSource<Int, UserLikedPhotoItem>(){
    override fun getRefreshKey(state: PagingState<Int, UserLikedPhotoItem>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserLikedPhotoItem> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getUserLikedPhotos(page = page, username = UserViewModel.username)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = { LoadResult.Error(it)}
        )
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}