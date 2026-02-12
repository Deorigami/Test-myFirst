package app.tktn.feature_search.landing

import app.tktn.service_news.domain.entity.NewsArticle

data class SearchScreenState(
    val articles: List<NewsArticle> = emptyList(),
    val query: String = "",
    val page: Int = 1,
    val isLastPage: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingNextPage: Boolean = false,
    val error: String? = null
)
