package com.avv2050soft.unsplashtool.domain.models.collectioninfo


import com.google.gson.annotations.SerializedName

data class LinksX(
    @SerializedName("html")
    val html: String,
    @SerializedName("photos")
    val photos: String,
    @SerializedName("related")
    val related: String,
    @SerializedName("self")
    val self: String
)