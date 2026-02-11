package app.tktn.attendees_check.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import androidx.navigation3.ui.NavDisplay
import androidx.navigationevent.NavigationEvent
import app.tktn.attendees_check.screen.home.HomeScreen
import app.tktn.core_feature.base.BaseScreen
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavDestinations : NavKey, NavDestinationScreen {

	data object Home : NavDestinations {
		override fun getScreen(): BaseScreen = HomeScreen
	}

	companion object {
		var backStack: SnapshotStateList<NavDestinations> =
			mutableStateListOf<NavDestinations>(Home)
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
