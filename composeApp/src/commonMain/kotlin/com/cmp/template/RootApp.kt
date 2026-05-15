package com.cmp.template
import androidx.compose.runtime.Composable
import androidx.navigation3.ui.NavDisplay
import com.cmp.template.navigation.NavDestinations
import com.cmp.template.navigation.setupNavigation
import com.cmp.template.theme.AppTheme
@Composable
internal fun RootApp() {
    AppTheme {
        NavDisplay(
            backStack = NavDestinations.backStack,
            onBack = {
                NavDestinations.backStack.removeLastOrNull()
            },
            entryProvider = { it.setupNavigation() }
        )
    }
}
