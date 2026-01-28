package app.tktn.attendees_check.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import androidx.navigation3.ui.NavDisplay
import androidx.navigationevent.NavigationEvent
import app.tktn.attendees_check.screen.splash.SplashScreen
import app.tktn.core_feature.base.BaseScreen
import app.tktn.feature_auth.auth.AuthScreen
import app.tktn.feature_dashboard.landing.DashboardLandingScreen
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavDestinations : NavKey, NavDestinationScreen {
    @Serializable @SerialName("SplashScreen") data object Splash : NavDestinations {
        override fun getScreen(): BaseScreen = SplashScreen
    }

    @Serializable @SerialName("AuthScreen") data object Auth : NavDestinations {
        override fun getScreen(): BaseScreen = AuthScreen
    }

    @Serializable @SerialName("DashboardLandingScreen") data object Dashboard : NavDestinations {
        override fun getScreen(): BaseScreen = DashboardLandingScreen
    }

    companion object {
        var backStack: SnapshotStateList<NavDestinations> = mutableStateListOf<NavDestinations>(Splash)
    }
}

fun NavDestinations.setupNavigation() : NavEntry<NavDestinations> {
    return route(this){
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
        transitionSpec?.let { putAll(NavDisplay.transitionSpec(transitionSpec)) }
        popTransitionSpec?.let { putAll(NavDisplay.popTransitionSpec(popTransitionSpec)) }
        predictivePopTransitionSpec?.let { putAll(NavDisplay.predictivePopTransitionSpec(predictivePopTransitionSpec)) }
    },
    content = { _ ->
        with(LocalNavAnimatedContentScope.current) {
            content()
        }
    },
)

typealias Scene<T> = androidx.navigation3.scene.Scene<T>
