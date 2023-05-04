package com.avv2050soft.unsplashtool.domain.models.userlikedphotos


import com.google.gson.annotations.SerializedName

data class TopicSubmissions(
    @SerializedName("film")
    val film: Film,
    @SerializedName("health")
    val health: Health,
    @SerializedName("nature")
    val nature: Nature,
    @SerializedName("people")
    val people: People,
    @SerializedName("spirituality")
    val spirituality: Spirituality,
    @SerializedName("travel")
    val travel: Travel,
    @SerializedName("wallpapers")
    val wallpapers: Wallpapers
)