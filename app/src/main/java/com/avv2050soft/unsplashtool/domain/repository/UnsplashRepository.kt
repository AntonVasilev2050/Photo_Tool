package com.avv2050soft.unsplashtool.domain.repository

import com.avv2050soft.unsplashtool.domain.models.likedPhoto.LikedPhoto
import com.avv2050soft.unsplashtool.domain.models.photo_details.PhotoDetails
import com.avv2050soft.unsplashtool.domain.models.photo_search.Result
import com.avv2050soft.unsplashtool.domain.models.photos.Photo

interface UnsplashRepository {

    suspend fun getPhotos(page : Int) : List<Photo>

    suspend fun getPhotoDetails(id : String): PhotoDetails

    suspend fun likePhoto(id: String): LikedPhoto

    suspend fun unlikePhoto(id: String): LikedPhoto

    suspend fun searchPhotos(page: Int, query: String) : List<Result>
}