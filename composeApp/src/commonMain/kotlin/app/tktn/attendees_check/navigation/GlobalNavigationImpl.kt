package app.tktn.attendees_check.navigation

import app.tktn.core_navigation.GlobalNavigation

abstract class GlobalNavigationImpl : GlobalNavigation {
	override fun navigateToNewsHome() {
		NavDestinations.backStack.add(NavDestinations.NewsHome)
	}
}