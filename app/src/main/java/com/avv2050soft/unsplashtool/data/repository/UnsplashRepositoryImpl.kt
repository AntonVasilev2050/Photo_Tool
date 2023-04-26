package com.avv2050soft.unsplashtool.data.repository

import com.avv2050soft.unsplashtool.data.api.UnsplashApi
import com.avv2050soft.unsplashtool.data.auth.TokenStorage
import com.avv2050soft.unsplashtool.domain.models.likedPhoto.LikedPhoto
import com.avv2050soft.unsplashtool.domain.models.photo_details.PhotoDetails
import com.avv2050soft.unsplashtool.domain.models.photo_search.Result
import com.avv2050soft.unsplashtool.domain.models.photo_search.SearchResponse
import com.avv2050soft.unsplashtool.domain.models.photos.Photo
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import javax.inject.Inject

class UnsplashRepositoryImpl @Inject constructor() : UnsplashRepository {

    private val accessToken = "Bearer ${TokenStorage.accessToken}"

    override suspend fun getPhotos(page: Int): List<Photo> {
        return UnsplashApi.create().getPhotos(token = accessToken, page = page)
    }

    override suspend fun getPhotoDetails(id: String): PhotoDetails {
        return UnsplashApi.create().getPhotoDetails(token = accessToken, id = id)
    }

    override suspend fun likePhoto(id: String): LikedPhoto {
        return UnsplashApi.create().likePhoto(token = accessToken, id = id)
    }

    override suspend fun unlikePhoto(id: String): LikedPhoto {
        return UnsplashApi.create().unlikePhoto(token = accessToken, id = id)
    }

    override suspend fun searchPhotos(page: Int, query: String): List<Result> {
        return UnsplashApi.create()
            .searchPhotos(token = accessToken, page = page, query = query).results
    }
}