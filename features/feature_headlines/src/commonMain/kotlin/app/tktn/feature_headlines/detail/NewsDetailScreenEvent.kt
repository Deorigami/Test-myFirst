package app.tktn.feature_headlines.detail

import app.tktn.service_news.domain.entity.NewsArticle

sealed class NewsDetailScreenEvent {
    data class ToggleBookmark(val article: NewsArticle) : NewsDetailScreenEvent()
}
