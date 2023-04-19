package com.avv2050soft.unsplashtool.data.db

import androidx.room.TypeConverter
import com.avv2050soft.unsplashtool.domain.models.photos.Links
import com.avv2050soft.unsplashtool.domain.models.photos.PhotosDbModelItem
import com.avv2050soft.unsplashtool.domain.models.photos.Urls
import com.avv2050soft.unsplashtool.domain.models.photos.User
import com.google.gson.Gson

class Converter {
//    @TypeConverter
//    fun listOfStringsToString(strings: List<String>): String {
//        val gson = Gson()
//        return gson.toJson(strings)
//    }
//
//    @TypeConverter
//    fun stringToListOfStrings(string: String): List<String> {
//        val gson = Gson()
////        val strings = ArrayList<String>()
////        for (any in listOfAny) {
////            strings.add(gson.fromJson(any.toString(), String::class.java))
////        }
//        return gson.fromJson(string, ArrayList::class.java) as List<String>
//    }

    @TypeConverter
    fun photosDbModelItemToString(photosDbModelItem: PhotosDbModelItem): String {
        val gson = Gson()
        return gson.toJson(photosDbModelItem)
    }

    @TypeConverter
    fun stringToPhotosDbModelItem(string: String): PhotosDbModelItem {
        val gson = Gson()
        return gson.fromJson(string, PhotosDbModelItem::class.java) as PhotosDbModelItem
    }

    @TypeConverter
    fun linksToString(links: Links): String{
        val gson = Gson()
        return gson.toJson(links)
    }

    @TypeConverter
    fun stringToLinks(string: String): Links {
        val gson = Gson()
        return gson.fromJson(string, Links::class.java) as Links
    }

    @TypeConverter
    fun urlsToString(urls: Urls): String{
        val gson = Gson()
        return gson.toJson(urls)
    }

    @TypeConverter
    fun stringToUrls(string: String): Urls {
        val gson = Gson()
        return gson.fromJson(string, Urls::class.java) as Urls
    }

    @TypeConverter
    fun userToString(user: User): String{
        val gson = Gson()
        return gson.toJson(user)
    }

    @TypeConverter
    fun stringToUser(string: String): User {
        val gson = Gson()
        return gson.fromJson(string, User::class.java) as User
    }
}