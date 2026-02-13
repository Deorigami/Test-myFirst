package app.tktn.service_news.domain.repository

import app.tktn.core_service.model.DomainResult
import app.tktn.service_news.domain.entity.NewsArticle
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopHeadlines(page: Int): DomainResult<List<NewsArticle>>
    suspend fun searchNews(query: String, page: Int): DomainResult<List<NewsArticle>>
    fun getBookmarkedNews(): Flow<List<NewsArticle>>
    fun getCachedHeadlines(): Flow<List<NewsArticle>>
    suspend fun toggleBookmark(article: NewsArticle)
    suspend fun isBookmarked(url: String): Boolean
    fun observeBookmarkStatus(url: String): Flow<Boolean>
}
