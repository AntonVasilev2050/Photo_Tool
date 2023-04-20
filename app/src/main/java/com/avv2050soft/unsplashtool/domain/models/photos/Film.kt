package com.avv2050soft.unsplashtool.domain.models.photos


import com.google.gson.annotations.SerializedName

data class Film(
    @SerializedName("approved_on")
    val approvedOn: String,
    @SerializedName("status")
    val status: String
)