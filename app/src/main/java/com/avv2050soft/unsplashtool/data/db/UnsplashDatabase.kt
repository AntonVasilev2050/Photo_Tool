package com.avv2050soft.unsplashtool.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.avv2050soft.unsplashtool.domain.models.photos.PhotosDbModelItem

@Database(
    entities = [PhotosDbModelItem::class],
    version = 1,
    exportSchema = false
)
abstract class UnsplashDatabase : RoomDatabase(){

    abstract fun photosDao(): PhotosDao

    companion object{
        private const val databaseName = "unsplash_photos"
        @Volatile
        private var INSTANCE: UnsplashDatabase? = null

        fun getInstance(context: Context): UnsplashDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UnsplashDatabase::class.java,
                    databaseName
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}