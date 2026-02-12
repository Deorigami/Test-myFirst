package app.tktn.attendees_check.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import androidx.navigation3.ui.NavDisplay
import androidx.navigationevent.NavigationEvent
import app.tktn.attendees_check.screen.home.NewsHomeScreen
import app.tktn.feature_headlines.landing.HeadlinesScreen
import app.tktn.feature_news.search.SearchScreen
import app.tktn.feature_news.detail.NewsDetailScreen
import app.tktn.attendees_check.screen.splash.SplashScreen
import app.tktn.core_feature.base.BaseScreen
import app.tktn.feature_bookmarks.landing.BookmarkScreen
import app.tktn.service_news.domain.entity.NewsArticle
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavDestinations : NavKey, NavDestinationScreen {

    data object NewsSplash : NavDestinations {
        override fun getScreen(): BaseScreen = SplashScreen
    }

    data object NewsHome : NavDestinations {
        override fun getScreen(): BaseScreen = NewsHomeScreen
    }

    data object Headlines : NavDestinations {
        override fun getScreen(): BaseScreen = HeadlinesScreen
    }

    data object Search : NavDestinations {
        override fun getScreen(): BaseScreen = SearchScreen
    }

    data object Bookmark : NavDestinations {
        override fun getScreen(): BaseScreen = BookmarkScreen
    }

    data class NewsDetail(val article: NewsArticle) : NavDestinations {
        override fun getScreen(): BaseScreen = NewsDetailScreen(article)
    }

	companion object {
		var backStack: SnapshotStateList<NavDestinations> =
			mutableStateListOf<NavDestinations>(NewsSplash)
	}
}

fun NavDestinations.setupNavigation(): NavEntry<NavDestinations> {
	return route(this) {
		this@setupNavigation.getScreen().ComposableScreen()
	}
}

interface NavDestinationScreen {
	fun getScreen(): BaseScreen
}

private inline fun <T : NavKey> route(
	key: T,
	noinline transitionSpec: (AnimatedContentTransitionScope<*>.() -> ContentTransform?)? = null,
	noinline popTransitionSpec: (AnimatedContentTransitionScope<*>.() -> ContentTransform?)? = null,
	metadata: Map<String, Any> = emptyMap(),
	noinline predictivePopTransitionSpec: (
	AnimatedContentTransitionScope<Scene<*>>.(swipe: @NavigationEvent.SwipeEdge Int) -> ContentTransform?
	)? = null,
	crossinline content: @Composable AnimatedContentScope.() -> Unit,
) = NavEntry(
	key = key,
	metadata = buildMap {
		putAll(metadata)
		transitionSpec?.let {
			putAll(
				NavDisplay.transitionSpec(
					transitionSpec
				)
			)
		}
		popTransitionSpec?.let {
			putAll(
				NavDisplay.popTransitionSpec(
					popTransitionSpec
				)
			)
		}
		predictivePopTransitionSpec?.let {
			putAll(
				NavDisplay.predictivePopTransitionSpec(
					predictivePopTransitionSpec
				)
			)
		}
	},
	content = { _ ->
		with(LocalNavAnimatedContentScope.current) {
			content()
		}
	},
)

typealias Scene<T> = androidx.navigation3.scene.Scene<T>
