package app.tktn.feature_headlines.detail

import app.tktn.service_news.domain.entity.NewsArticle

data class NewsDetailScreenState(
    val article: NewsArticle? = null,
    val isBookmarked: Boolean = false
)
