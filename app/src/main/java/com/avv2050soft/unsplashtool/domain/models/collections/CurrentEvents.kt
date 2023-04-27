package com.avv2050soft.unsplashtool.domain.models.collections


import com.google.gson.annotations.SerializedName

data class CurrentEvents(
    @SerializedName("approved_on")
    val approvedOn: String,
    @SerializedName("status")
    val status: String
)