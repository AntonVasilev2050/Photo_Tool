package com.avv2050soft.unsplashtool.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avv2050soft.unsplashtool.domain.models.photos.PhotosDbModelItem

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photosDbModelItem: PhotosDbModelItem)

    @Query("SELECT * FROM photos_db_model_items")
    suspend fun getAllPhotosFromDb(): List<PhotosDbModelItem>
}