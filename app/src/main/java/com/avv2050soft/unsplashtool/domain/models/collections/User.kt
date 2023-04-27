package com.avv2050soft.unsplashtool.domain.models.collections


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("bio")
    val bio: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("links")
    val links: LinksX,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("portfolio_url")
    val portfolioUrl: String,
    @SerializedName("profile_image")
    val profileImage: ProfileImage,
    @SerializedName("total_collections")
    val totalCollections: Int,
    @SerializedName("total_likes")
    val totalLikes: Int,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("username")
    val username: String
)