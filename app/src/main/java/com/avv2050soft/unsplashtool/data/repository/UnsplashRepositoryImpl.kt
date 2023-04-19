package com.avv2050soft.unsplashtool.data.repository

import com.avv2050soft.unsplashtool.data.api.UnsplashApi
import com.avv2050soft.unsplashtool.data.auth.TokenStorage
import com.avv2050soft.unsplashtool.domain.models.photos.PhotosDbModelItem
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import javax.inject.Inject

class UnsplashRepositoryImpl @Inject constructor() : UnsplashRepository{
    override suspend fun getPhotos(token: String): List<PhotosDbModelItem> {
        val accessToken = "Bearer ${TokenStorage.accessToken}"
        return UnsplashApi.create().getPhotos(token = accessToken)
    }
}