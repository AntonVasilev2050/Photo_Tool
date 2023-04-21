package com.avv2050soft.unsplashtool.domain.repository

import com.avv2050soft.unsplashtool.domain.models.photo_details.PhotoDetails
import com.avv2050soft.unsplashtool.domain.models.photos.Photo

interface UnsplashRepository {

    suspend fun getPhotos(page : Int) : List<Photo>

    suspend fun getPhotoDetails(id : String): PhotoDetails
}