package com.cmp.template.navigation
import com.cmp.template.core_navigation.GlobalNavigation
abstract class GlobalNavigationImpl : GlobalNavigation {
    override fun back() {
        NavDestinations.backStack.removeLast()
    }
    override fun navigateToHome() {
        NavDestinations.backStack.add(NavDestinations.Home)
    }
    // TODO: Add navigate methods for additional destinations here
}
