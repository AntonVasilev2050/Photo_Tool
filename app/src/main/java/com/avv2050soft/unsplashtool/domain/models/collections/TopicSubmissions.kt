package com.avv2050soft.unsplashtool.domain.models.collections


import com.google.gson.annotations.SerializedName

data class TopicSubmissions(
    @SerializedName("architecture-interior")
    val architectureInterior: ArchitectureInterior,
    @SerializedName("arts-culture")
    val artsCulture: ArtsCulture,
    @SerializedName("athletics")
    val athletics: Athletics,
    @SerializedName("business-work")
    val businessWork: BusinessWork,
    @SerializedName("experimental")
    val experimental: Experimental,
    @SerializedName("fashion-beauty")
    val fashionBeauty: FashionBeauty,
    @SerializedName("food-drink")
    val foodDrink: FoodDrink,
    @SerializedName("girls-vs-stereotypes")
    val girlsVsStereotypes: GirlsVsStereotypes,
    @SerializedName("health")
    val health: Health,
    @SerializedName("interiors")
    val interiors: Interiors,
    @SerializedName("nature")
    val nature: Nature,
    @SerializedName("textures-patterns")
    val texturesPatterns: TexturesPatterns,
    @SerializedName("travel")
    val travel: Travel,
    @SerializedName("wallpapers")
    val wallpapers: Wallpapers
)