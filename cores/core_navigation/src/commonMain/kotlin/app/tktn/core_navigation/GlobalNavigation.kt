package app.tktn.core_navigation

import app.tktn.service_news.domain.entity.NewsArticle

interface GlobalNavigation {
	fun back()
	fun navigateToNewsHome()
	fun navigateToNewsDetail(newsArticle: NewsArticle)
}