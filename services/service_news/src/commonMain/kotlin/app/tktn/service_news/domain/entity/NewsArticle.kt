package app.tktn.service_news.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class NewsArticle(
    val title: String,
    val author: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    val isBookmarked: Boolean = false
)
