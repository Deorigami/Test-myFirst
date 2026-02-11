package app.tktn.feature_feed.landing.model

data class Post(
    val id: String,
    val username: String,
    val userAvatar: String,
    val hasVideo: Boolean,
    val videoUrl: String?,
    val images: List<String>,
    val likesCount: Int,
    val caption: String,
    val commentCount: Int,
    val timeAgo: String
)
