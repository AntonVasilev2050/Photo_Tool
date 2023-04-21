package com.avv2050soft.unsplashtool.domain.models.photo_details


import com.google.gson.annotations.SerializedName

data class Exif(
    @SerializedName("aperture")
    val aperture: String,
    @SerializedName("exposure_time")
    val exposureTime: String,
    @SerializedName("focal_length")
    val focalLength: String,
    @SerializedName("iso")
    val iso: Int,
    @SerializedName("make")
    val make: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("name")
    val name: String
)