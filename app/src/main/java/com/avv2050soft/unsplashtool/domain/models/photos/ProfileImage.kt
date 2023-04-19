package com.avv2050soft.unsplashtool.domain.models.photos


import com.google.gson.annotations.SerializedName

data class ProfileImage(
    @SerializedName("large")
    val large: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("small")
    val small: String
)