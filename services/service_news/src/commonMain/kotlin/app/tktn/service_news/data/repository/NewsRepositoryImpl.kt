package app.tktn.service_news.data.repository

import app.tktn.core_service.model.DomainResult
import app.tktn.service_news.data.local.dao.NewsDao
import app.tktn.service_news.data.local.entity.NewsArticleEntity
import app.tktn.service_news.data.remote.NewsApi
import app.tktn.service_news.domain.entity.NewsArticle
import app.tktn.service_news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class NewsRepositoryImpl(
	private val api: NewsApi,
	private val dao: NewsDao
) : NewsRepository {

	private val apiKey =
		"79a1436988f44403a63332db5b80c840" // I'll use this for now as a default for testing, but should be configurable

	override suspend fun getTopHeadlines(page: Int): DomainResult<List<NewsArticle>> {
		val response = api.getTopHeadlines(page = page, apiKey = apiKey)
		val articles = response.articles.map { dto ->
			NewsArticle(
				title = dto.title ?: "",
				author = dto.author,
				description = dto.description,
				url = dto.url ?: "",
				urlToImage = dto.urlToImage,
				publishedAt = dto.publishedAt ?: "",
				content = dto.content
			)
		}

		if (page == 1) {
			dao.clearTopHeadlines()
		}

		val entities = articles.map { article ->
			val existing = dao.getArticleByUrl(article.url)
			NewsArticleEntity(
				url = article.url,
				title = article.title,
				author = article.author,
				description = article.description,
				urlToImage = article.urlToImage,
				publishedAt = article.publishedAt,
				content = article.content,
				isTopHeadline = true,
				isBookmarked = existing?.isBookmarked ?: false
			)
		}
		dao.insertArticles(entities)

		return DomainResult(articles)
	}

	override suspend fun searchNews(
		query: String,
		page: Int
	): DomainResult<List<NewsArticle>> {
		val response = api.searchNews(
			query = query,
			page = page,
			apiKey = apiKey
		)
		val articles = response.articles.map { dto ->
			val existing = dao.getArticleByUrl(dto.url ?: "")
			NewsArticle(
				title = dto.title ?: "",
				author = dto.author,
				description = dto.description,
				url = dto.url ?: "",
				urlToImage = dto.urlToImage,
				publishedAt = dto.publishedAt ?: "",
				content = dto.content,
				isBookmarked = existing?.isBookmarked ?: false
			)
		}
		return DomainResult(articles)
	}

	override fun getBookmarkedNews(): Flow<List<NewsArticle>> {
		return dao.getBookmarkedArticles().map { entities ->
			entities.map { it.toDomain() }
		}
	}

	override fun getCachedHeadlines(): Flow<List<NewsArticle>> {
		return dao.getTopHeadlines().map { entities ->
			entities.map { it.toDomain() }
		}
	}

	override suspend fun toggleBookmark(article: NewsArticle) {
		val existing = dao.getArticleByUrl(article.url)
		if (existing != null) {
			dao.updateBookmarkStatus(
				article.url,
				!existing.isBookmarked
			)
		} else {
			dao.insertArticles(
				listOf(
					NewsArticleEntity(
						url = article.url,
						title = article.title,
						author = article.author,
						description = article.description,
						urlToImage = article.urlToImage,
						publishedAt = article.publishedAt,
						content = article.content,
						isTopHeadline = false,
						isBookmarked = true
					)
				)
			)
		}
	}

	override suspend fun isBookmarked(url: String): Boolean {
		return dao.getArticleByUrl(url)?.isBookmarked ?: false
	}

	private fun NewsArticleEntity.toDomain() = NewsArticle(
		title = title,
		author = author,
		description = description,
		url = url,
		urlToImage = urlToImage,
		publishedAt = publishedAt,
		content = content,
		isBookmarked = isBookmarked
	)
}
