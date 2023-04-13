package com.avv2050soft.unsplashtool.data.db.models


import com.google.gson.annotations.SerializedName

data class TopicSubmissions(
    @SerializedName("film")
    val film: Film,
    @SerializedName("nature")
    val nature: Nature,
    @SerializedName("people")
    val people: People,
    @SerializedName("spirituality")
    val spirituality: Spirituality,
    @SerializedName("textures-patterns")
    val texturesPatterns: TexturesPatterns,
    @SerializedName("wallpapers")
    val wallpapers: Wallpapers
)