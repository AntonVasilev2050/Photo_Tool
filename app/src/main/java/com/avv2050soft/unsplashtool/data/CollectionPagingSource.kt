package com.avv2050soft.unsplashtool.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.avv2050soft.unsplashtool.domain.models.collections.CollectionsListItem
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import javax.inject.Inject

class CollectionPagingSource @Inject constructor(
    private val repository: UnsplashRepository
) : PagingSource<Int, CollectionsListItem>() {
    override fun getRefreshKey(state: PagingState<Int, CollectionsListItem>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionsListItem> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getCollection(page)
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