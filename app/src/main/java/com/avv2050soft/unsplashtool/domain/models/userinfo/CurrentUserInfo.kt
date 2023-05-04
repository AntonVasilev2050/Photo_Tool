package com.avv2050soft.unsplashtool.domain.models.userinfo


import com.google.gson.annotations.SerializedName

data class CurrentUserInfo(
    @SerializedName("accepted_tos")
    val acceptedTos: Boolean,
    @SerializedName("allow_messages")
    val allowMessages: Boolean,
    @SerializedName("badge")
    val badge: Any,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("confirmed")
    val confirmed: Boolean,
    @SerializedName("dmca_verification")
    val dmcaVerification: String,
    @SerializedName("downloads")
    val downloads: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("followed_by_user")
    val followedByUser: Boolean,
    @SerializedName("followers_count")
    val followersCount: Int,
    @SerializedName("following_count")
    val followingCount: Int,
    @SerializedName("for_hire")
    val forHire: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("instagram_username")
    val instagramUsername: Any,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("links")
    val links: Links,
    @SerializedName("location")
    val location: String?,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("name")
    val name: String,
    @SerializedName("numeric_id")
    val numericId: Int,
    @SerializedName("photos")
    val photos: List<Any>,
    @SerializedName("portfolio_url")
    val portfolioUrl: Any,
    @SerializedName("profile_image")
    val profileImage: ProfileImage,
    @SerializedName("social")
    val social: Social,
    @SerializedName("tags")
    val tags: Tags,
    @SerializedName("total_collections")
    val totalCollections: Int,
    @SerializedName("total_likes")
    val totalLikes: Int,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("twitter_username")
    val twitterUsername: Any,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("unlimited_uploads")
    val unlimitedUploads: Boolean,
    @SerializedName("unread_highlight_notifications")
    val unreadHighlightNotifications: Boolean,
    @SerializedName("unread_in_app_notifications")
    val unreadInAppNotifications: Boolean,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("uploads_remaining")
    val uploadsRemaining: Int,
    @SerializedName("username")
    val username: String
)