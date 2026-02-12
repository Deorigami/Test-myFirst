package app.tktn.feature_headlines.landing

import app.tktn.service_news.domain.entity.NewsArticle

sealed class HeadlinesScreenEvent {
    object LoadHeadlines : HeadlinesScreenEvent()
    object LoadNextPage : HeadlinesScreenEvent()
    object Refresh : HeadlinesScreenEvent()
    data class ToggleBookmark(val article: NewsArticle) : HeadlinesScreenEvent()
}
