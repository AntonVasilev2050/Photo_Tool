package com.avv2050soft.unsplashtool.domain.models.photo_details


import com.google.gson.annotations.SerializedName

data class FashionBeauty(
    @SerializedName("approved_on")
    val approvedOn: String,
    @SerializedName("status")
    val status: String
)