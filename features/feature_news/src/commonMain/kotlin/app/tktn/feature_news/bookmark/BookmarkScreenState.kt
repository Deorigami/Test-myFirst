package app.tktn.feature_news.bookmark

import app.tktn.service_news.domain.entity.NewsArticle

data class BookmarkScreenState(
    val articles: List<NewsArticle> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class BookmarkScreenEvent {
    object LoadBookmarks : BookmarkScreenEvent()
    data class ToggleBookmark(val article: NewsArticle) : BookmarkScreenEvent()
}
