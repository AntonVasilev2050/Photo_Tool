package com.avv2050soft.unsplashtool.domain.repository

import com.avv2050soft.unsplashtool.domain.models.photos.Photo

interface DatabaseRepository {
    suspend fun insert(photo: Photo)

    suspend fun getAllPhotosFromDb() : List<Photo>

    suspend fun deleteAllPhotosFromDb()
}