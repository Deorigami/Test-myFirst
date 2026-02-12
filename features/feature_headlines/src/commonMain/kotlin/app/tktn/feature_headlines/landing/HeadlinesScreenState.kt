package app.tktn.feature_headlines.landing

import app.tktn.service_news.domain.entity.NewsArticle

data class HeadlinesScreenState(
    val articles: List<NewsArticle> = emptyList(),
    val page: Int = 1,
    val isLastPage: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingNextPage: Boolean = false,
    val error: String? = null,
    val isOffline: Boolean = false
)
