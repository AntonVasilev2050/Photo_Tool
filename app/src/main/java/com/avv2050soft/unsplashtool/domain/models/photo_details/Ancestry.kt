package com.avv2050soft.unsplashtool.domain.models.photo_details


import com.google.gson.annotations.SerializedName

data class Ancestry(
    @SerializedName("category")
    val category: Category,
    @SerializedName("subcategory")
    val subcategory: Subcategory,
    @SerializedName("type")
    val type: Type
)