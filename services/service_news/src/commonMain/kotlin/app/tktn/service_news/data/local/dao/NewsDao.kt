package app.tktn.service_news.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import app.tktn.service_news.data.local.entity.NewsArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
	@Transaction
    @Query("SELECT * FROM news_articles WHERE isTopHeadline = 1")
    fun getTopHeadlines(): Flow<List<NewsArticleEntity>>

	@Transaction
	@Query("SELECT * FROM news_articles WHERE isBookmarked = 1")
    fun getBookmarkedArticles(): Flow<List<NewsArticleEntity>>

	@Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<NewsArticleEntity>)

	@Transaction
    @Query("UPDATE news_articles SET isBookmarked = :isBookmarked WHERE url = :url")
    suspend fun updateBookmarkStatus(url: String, isBookmarked: Boolean)

	@Transaction
    @Query("SELECT * FROM news_articles WHERE url = :url")
    suspend fun getArticleByUrl(url: String): NewsArticleEntity?

	@Transaction
    @Query("DELETE FROM news_articles WHERE isTopHeadline = 1 AND isBookmarked = 0")
    suspend fun clearTopHeadlines()
}
