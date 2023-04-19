package com.avv2050soft.unsplashtool.domain.repository

import com.avv2050soft.unsplashtool.domain.models.photos.PhotosDbModelItem

interface UnsplashRepository {

    suspend fun getPhotos(token : String) : List<PhotosDbModelItem>
}