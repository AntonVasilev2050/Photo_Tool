package com.avv2050soft.unsplashtool.domain.repository

import com.avv2050soft.unsplashtool.domain.models.collectioninfo.CollectionInfo
import com.avv2050soft.unsplashtool.domain.models.collectionphotos.CollectionPhotoItem
import com.avv2050soft.unsplashtool.domain.models.collections.CollectionsItem
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
    suspend fun getCollection(page: Int): List<CollectionsItem>
    suspend fun getThisCollectionInfo(id: String) : CollectionInfo
    suspend fun getThisCollectionPhotos(page: Int, id: String) : List<CollectionPhotoItem>
}