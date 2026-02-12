package app.tktn.service_news.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_articles")
data class NewsArticleEntity(
    @PrimaryKey val url: String,
    val title: String,
    val author: String?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    val isTopHeadline: Boolean = false,
    val isBookmarked: Boolean = false
)
