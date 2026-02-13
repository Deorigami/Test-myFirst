package app.tktn.service_news.data.repository

import app.tktn.service_news.data.local.dao.NewsDao
import app.tktn.service_news.data.local.entity.NewsArticleEntity
import app.tktn.service_news.data.remote.NewsApi
import app.tktn.service_news.data.remote.dto.ArticleDto
import app.tktn.service_news.data.remote.dto.NewsResponse
import app.tktn.service_news.domain.entity.NewsArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NewsRepositoryTest {

    private lateinit var fakeApi: FakeNewsApi
    private lateinit var fakeDao: FakeNewsDao
    private lateinit var repository: NewsRepositoryImpl

    @BeforeTest
    fun setup() {
        fakeApi = FakeNewsApi()
        fakeDao = FakeNewsDao()
        repository = NewsRepositoryImpl(fakeApi, fakeDao)
    }

    @Test
    fun `getTopHeadlines returns articles and caches them`() = runTest {
        // Arrange
        val remoteArticles = listOf(
            createArticleDto("Title 1", "http://url1.com"),
            createArticleDto("Title 2", "http://url2.com")
        )
        fakeApi.topHeadlinesResponse = NewsResponse("ok", 2, remoteArticles)

        // Act
        val result = repository.getTopHeadlines(1)

        // Assert
        assertTrue(result.status)
        assertEquals(2, result.data?.size)
        assertEquals("Title 1", result.data?.first()?.title)

        // Verify caching (Dao should have inserted them as top headlines)
        val cached = fakeDao.getTopHeadlines().first()
        assertEquals(2, cached.size)
        assertEquals("http://url1.com", cached.first().url)
        assertTrue(cached.first().isTopHeadline)
    }

    @Test
    fun `searchNews returns articles from api`() = runTest {
        // Arrange
        val searchResults = listOf(
            createArticleDto("Search Result 1", "http://search1.com")
        )
        fakeApi.searchResponse = NewsResponse("ok", 1, searchResults)

        // Act
        val result = repository.searchNews("query", 1)

        // Assert
        assertTrue(result.status)
        assertEquals(1, result.data?.size)
        assertEquals("Search Result 1", result.data?.first()?.title)
    }

    @Test
    fun `toggleBookmark saves article as bookmarked`() = runTest {
        // Arrange
        val article = createNewsArticle("Title 1", "http://url1.com")

        // Act
        repository.toggleBookmark(article)

        // Assert
        val bookmarked = fakeDao.getBookmarkedArticles().first()
        assertEquals(1, bookmarked.size)
        assertEquals("http://url1.com", bookmarked.first().url)
        assertTrue(bookmarked.first().isBookmarked)
    }

    @Test
    fun `toggleBookmark removes bookmark if already bookmarked`() = runTest {
        // Arrange
        val article = createNewsArticle("Title 1", "http://url1.com")
        repository.toggleBookmark(article) // Bookmark it first

        // Act
        repository.toggleBookmark(article) // Toggle again (un-bookmark)

        // Assert
        val bookmarked = fakeDao.getBookmarkedArticles().first()
        assertTrue(bookmarked.isEmpty())
        
        // Check stored entity status
        val stored = fakeDao.getArticleByUrl("http://url1.com")
        // If it was only bookmarked and not top headline, it might still exist with isBookmarked=false or implementation dependent
        // The repository implementation doesn't delete, it just updates status.
        // Wait, repository calls `updateBookmarkStatus`
        
        if (stored != null) {
            assertFalse(stored.isBookmarked)
        }
    }

    @Test
    fun `isBookmarked returns correct status`() = runTest {
         // Arrange
        val article = createNewsArticle("Title 1", "http://url1.com")
        repository.toggleBookmark(article)

        // Act
        val isBookmarked = repository.isBookmarked("http://url1.com")
        val isNotBookmarked = repository.isBookmarked("http://other.com")

        // Assert
        assertTrue(isBookmarked)
        assertFalse(isNotBookmarked)
    }

    // Helpers

    private fun createArticleDto(title: String, url: String) = ArticleDto(
        source = null,
        author = "Author",
        title = title,
        description = "Desc",
        url = url,
        urlToImage = null,
        publishedAt = "2024-01-01",
        content = "Content"
    )

    private fun createNewsArticle(title: String, url: String) = NewsArticle(
        title = title,
        author = "Author",
        description = "Desc",
        url = url,
        urlToImage = null,
        publishedAt = "2024-01-01",
        content = "Content",
        isBookmarked = false
    )
}

// Fakes

class FakeNewsApi : NewsApi {
    var topHeadlinesResponse = NewsResponse("ok", 0, emptyList())
    var searchResponse = NewsResponse("ok", 0, emptyList())

    override suspend fun getTopHeadlines(
        country: String,
        pageSize: Int,
        page: Int,
        apiKey: String
    ): NewsResponse {
        return topHeadlinesResponse
    }

    override suspend fun searchNews(
        query: String,
        pageSize: Int,
        page: Int,
        apiKey: String
    ): NewsResponse {
        return searchResponse
    }
}

class FakeNewsDao : NewsDao {

    private val _articles = MutableStateFlow<List<NewsArticleEntity>>(emptyList())

    override fun getTopHeadlines(): Flow<List<NewsArticleEntity>> {
        return _articles.map { list -> list.filter { it.isTopHeadline } }
    }

    override fun getBookmarkedArticles(): Flow<List<NewsArticleEntity>> {
        return _articles.map { list -> list.filter { it.isBookmarked } }
    }

    override suspend fun insertArticles(articles: List<NewsArticleEntity>) {
        _articles.update { current ->
            val newMap = current.associateBy { it.url }.toMutableMap()
            articles.forEach { article ->
                newMap[article.url] = article
            }
            newMap.values.toList()
        }
    }

    override suspend fun updateBookmarkStatus(url: String, isBookmarked: Boolean) {
        _articles.update { current ->
            current.map { article ->
                if (article.url == url) {
                    article.copy(isBookmarked = isBookmarked)
                } else {
                    article
                }
            }
        }
    }

    override suspend fun getArticleByUrl(url: String): NewsArticleEntity? {
        return _articles.value.find { it.url == url }
    }

    override suspend fun clearTopHeadlines() {
        _articles.update { current ->
            // Delete if isTopHeadline AND isBookmarked=0
            // Basically remove top headline flag, or delete row if it's not bookmarked
            // The Dao query is: DELETE FROM news_articles WHERE isTopHeadline = 1 AND isBookmarked = 0
            // But if isBookmarked = 1, it presumably keeps it but maybe should clear isTopHeadline status?
            // The REAL Dao implementation likely removes the rows. 
            // Repository implementation logic:
            // It calls clearTopHeadlines() before inserting new ones.
            
            // To faithfully mimic the SQL:
            current.filterNot { it.isTopHeadline && !it.isBookmarked }
            // Note: Use case logic implies we want to replace top headlines.
            // If an article is bookmarked, we keep it. If it was top headline, it effectively loses that status unless re-inserted.
            // But wait, if I delete the row, I lose the bookmark too if the condition matches?
            // Ah, SQL says: WHERE isTopHeadline = 1 AND isBookmarked = 0.
            // So if isBookmarked is 1, it is NOT deleted.
            // But does it lose isTopHeadline status? The SQL delete removes the ROW.
            // So if isBookmarked=1, the row stays. It remains isTopHeadline=1 unless updated.
            // The Repository then inserts new articles.
            // If a new article matches the existing one, `insertArticles` with REPLACE strategy will overwrite it, 
            // and the Repository sets `isTopHeadline=true` for new ones.
            // The Repository also sets `isBookmarked` based on `existing?.isBookmarked`.
            
            // So my fake implementation of `clearTopHeadlines` should just delete rows that preserve the SQL logic.
        }
        
        // Wait, if I delete rows where (isTopHeadline=1 AND isBookmarked=0),
        // Then rows with (isTopHeadline=1 AND isBookmarked=1) remain.
        // But do they remain as Top Headlines?
        // Yes, existing rows are untouched.
        // Then `insertArticles` comes along.
    }
}
