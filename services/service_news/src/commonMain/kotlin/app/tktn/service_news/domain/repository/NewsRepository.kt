package app.tktn.service_news.domain.repository

import app.tktn.service_news.domain.entity.NewsArticle
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopHeadlines(page: Int): Result<List<NewsArticle>>
    suspend fun searchNews(query: String, page: Int): Result<List<NewsArticle>>
    fun getBookmarkedNews(): Flow<List<NewsArticle>>
    fun getCachedHeadlines(): Flow<List<NewsArticle>>
    suspend fun toggleBookmark(article: NewsArticle)
    suspend fun isBookmarked(url: String): Boolean
}
