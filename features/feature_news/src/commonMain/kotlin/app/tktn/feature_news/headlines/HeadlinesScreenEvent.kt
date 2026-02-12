package app.tktn.feature_news.headlines

import app.tktn.service_news.domain.entity.NewsArticle

sealed class HeadlinesScreenEvent {
    object LoadHeadlines : HeadlinesScreenEvent()
    object LoadNextPage : HeadlinesScreenEvent()
    object Refresh : HeadlinesScreenEvent()
    data class ToggleBookmark(val article: NewsArticle) : HeadlinesScreenEvent()
}
