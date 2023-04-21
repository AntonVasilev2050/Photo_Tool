package com.avv2050soft.unsplashtool.domain.models.photo_details


import com.google.gson.annotations.SerializedName

data class TopicSubmissions(
    @SerializedName("experimental")
    val experimental: Experimental,
    @SerializedName("textures-patterns")
    val texturesPatterns: TexturesPatterns
)