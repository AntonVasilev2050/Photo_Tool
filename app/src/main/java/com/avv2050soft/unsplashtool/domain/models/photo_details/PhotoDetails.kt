package com.avv2050soft.unsplashtool.domain.models.photo_details


import com.google.gson.annotations.SerializedName

data class PhotoDetails(
    @SerializedName("alt_description")
    val altDescription: String,
    @SerializedName("blur_hash")
    val blurHash: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("current_user_collections")
    val currentUserCollections: List<Any>,
    @SerializedName("description")
    val description: String,
    @SerializedName("downloads")
    val downloads: Int,
    @SerializedName("exif")
    val exif: Exif,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("links")
    val links: Links,
    @SerializedName("location")
    val location: Location,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("promoted_at")
    val promotedAt: String,
    @SerializedName("public_domain")
    val publicDomain: Boolean,
    @SerializedName("related_collections")
    val relatedCollections: RelatedCollections,
    @SerializedName("sponsorship")
    val sponsorship: Any,
    @SerializedName("tags")
    val tags: List<Tag>,
    @SerializedName("tags_preview")
    val tagsPreview: List<TagsPreview>,
    @SerializedName("topic_submissions")
    val topicSubmissions: TopicSubmissions,
    @SerializedName("topics")
    val topics: List<Any>,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("urls")
    val urls: Urls,
    @SerializedName("user")
    val user: User,
    @SerializedName("views")
    val views: Int,
    @SerializedName("width")
    val width: Int
)