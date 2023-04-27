package com.avv2050soft.unsplashtool.domain.models.collectioninfo


import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String
)