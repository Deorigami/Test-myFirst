package app.tktn.attendees_check

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.toMutableStateList
import androidx.navigation3.ui.NavDisplay
import app.tktn.attendees_check.navigation.NavDestinations
import app.tktn.attendees_check.navigation.setupNavigation
import app.tktn.attendees_check.theme.AppTheme
import app.tktn.core_feature.navigation.LocalNavStack

@Composable
internal fun RootApp() {
    AppTheme {
        CompositionLocalProvider(
            LocalNavStack provides NavDestinations.backStack.toList().toMutableStateList()
        ) {
            NavDisplay(
                backStack = NavDestinations.backStack,
                onBack = {
                    NavDestinations.backStack.removeLastOrNull()
                },
                entryProvider = { it.setupNavigation() }
            )
        }
    }
}
