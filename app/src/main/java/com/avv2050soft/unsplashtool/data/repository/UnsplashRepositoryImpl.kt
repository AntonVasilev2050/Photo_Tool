package com.avv2050soft.unsplashtool.data.repository

import com.avv2050soft.unsplashtool.data.api.UnsplashApi
import com.avv2050soft.unsplashtool.data.auth.TokenStorage
import com.avv2050soft.unsplashtool.domain.models.collectioninfo.CollectionInfo
import com.avv2050soft.unsplashtool.domain.models.collectionphotos.CollectionPhotoItem
import com.avv2050soft.unsplashtool.domain.models.collections.CollectionsItem
import com.avv2050soft.unsplashtool.domain.models.likedPhoto.LikedPhoto
import com.avv2050soft.unsplashtool.domain.models.photo_details.PhotoDetails
import com.avv2050soft.unsplashtool.domain.models.photo_search.Result
import com.avv2050soft.unsplashtool.domain.models.photos.Photo
import com.avv2050soft.unsplashtool.domain.models.userinfo.CurrentUserInfo
import com.avv2050soft.unsplashtool.domain.models.userlikedphotos.UserLikedPhotoItem
import com.avv2050soft.unsplashtool.domain.repository.UnsplashRepository
import javax.inject.Inject

class UnsplashRepositoryImpl @Inject constructor() : UnsplashRepository {

    companion object{
        var accessToken = "Bearer ${TokenStorage.accessToken}"
    }

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

    override suspend fun getCollection(page: Int): List<CollectionsItem> {
        return UnsplashApi.create().getCollections(token = accessToken, page = page)
    }

    override suspend fun getThisCollectionInfo(id: String): CollectionInfo {
        return UnsplashApi.create().getThisCollectionInfo(token = accessToken, id = id)
    }

    override suspend fun getThisCollectionPhotos(page: Int, id: String): List<CollectionPhotoItem> {
        return UnsplashApi.create()
            .getThisCollectionPhotos(token = accessToken, page = page, id = id)
    }

    override suspend fun getCurrentUserInfo(): CurrentUserInfo {
        return UnsplashApi.create().getCurrentUserInfo(token = accessToken)
    }

    override suspend fun getUserLikedPhotos(
        username: String,
        page: Int
    ): List<UserLikedPhotoItem> {
        return UnsplashApi.create()
            .getUserLikedPhotos(token = accessToken, userName = username, page = page)
    }
}