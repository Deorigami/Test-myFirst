package app.tktn.feature_feed.landing

import app.tktn.feature_feed.landing.model.Post

data class FeedScreenState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList()
)