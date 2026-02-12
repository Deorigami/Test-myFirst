package app.tktn.service_news.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.tktn.service_news.data.local.entity.NewsArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_articles WHERE isTopHeadline = 1")
    fun getTopHeadlines(): Flow<List<NewsArticleEntity>>

    @Query("SELECT * FROM news_articles WHERE isBookmarked = 1")
    fun getBookmarkedArticles(): Flow<List<NewsArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<NewsArticleEntity>)

    @Query("UPDATE news_articles SET isBookmarked = :isBookmarked WHERE url = :url")
    suspend fun updateBookmarkStatus(url: String, isBookmarked: Boolean)
    
    @Query("SELECT * FROM news_articles WHERE url = :url")
    suspend fun getArticleByUrl(url: String): NewsArticleEntity?

    @Query("DELETE FROM news_articles WHERE isTopHeadline = 1 AND isBookmarked = 0")
    suspend fun clearTopHeadlines()
}
