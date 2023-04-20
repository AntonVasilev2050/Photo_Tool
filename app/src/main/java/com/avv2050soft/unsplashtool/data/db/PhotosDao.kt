package com.avv2050soft.unsplashtool.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avv2050soft.unsplashtool.domain.models.photos.Photo

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: Photo)

    @Query("SELECT * FROM photos_db_model_items")
    suspend fun getAllPhotosFromDb(): List<Photo>
}