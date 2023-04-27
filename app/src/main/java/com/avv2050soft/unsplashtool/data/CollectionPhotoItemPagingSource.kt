package com.avv2050soft.unsplashtool.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.avv2050soft.unsplashtool.domain.models.collectionphotos.CollectionPhotoItem
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import com.avv2050soft.unsplashtool.presentation.CollectionDetailsViewModel
import javax.inject.Inject

class CollectionPhotoItemPagingSource @Inject constructor(
    private val repository: UnsplashRepository
) : PagingSource<Int, CollectionPhotoItem>() {

    override fun getRefreshKey(state: PagingState<Int, CollectionPhotoItem>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionPhotoItem> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getThisCollectionPhotos(page, CollectionDetailsViewModel.collectionId)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    companion object {
        private const val FIRST_PAGE = 1
    }

}