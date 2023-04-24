package com.avv2050soft.unsplashtool.domain.models.likedPhoto


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: String,
    @SerializedName("links")
    val links: LinksX,
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val username: String
)