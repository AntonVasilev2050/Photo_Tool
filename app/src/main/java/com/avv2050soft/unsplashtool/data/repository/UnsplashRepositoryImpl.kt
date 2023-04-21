package com.avv2050soft.unsplashtool.data.repository

import com.avv2050soft.unsplashtool.data.api.UnsplashApi
import com.avv2050soft.unsplashtool.data.auth.TokenStorage
import com.avv2050soft.unsplashtool.domain.models.photo_details.PhotoDetails
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
}