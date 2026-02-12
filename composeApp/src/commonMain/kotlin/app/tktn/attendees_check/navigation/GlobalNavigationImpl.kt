package app.tktn.attendees_check.navigation

import app.tktn.core_navigation.GlobalNavigation
import app.tktn.service_news.domain.entity.NewsArticle

abstract class GlobalNavigationImpl : GlobalNavigation {
	override fun back() {
		NavDestinations.backStack.removeLast()
	}

	override fun navigateToNewsHome() {
		NavDestinations.backStack.add(NavDestinations.NewsHome)
	}

	override fun navigateToNewsDetail(newsArticle: NewsArticle) {
		NavDestinations.backStack.add(NavDestinations.NewsDetail(newsArticle))
	}
}