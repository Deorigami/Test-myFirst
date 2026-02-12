package app.tktn.feature_news.search

import app.tktn.service_news.domain.entity.NewsArticle

sealed class SearchScreenEvent {
    data class UpdateQuery(val query: String) : SearchScreenEvent()
    object Search : SearchScreenEvent()
    object LoadNextPage : SearchScreenEvent()
    data class ToggleBookmark(val article: NewsArticle) : SearchScreenEvent()
}
