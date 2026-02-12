package app.tktn.attendees_check.navigation

import app.tktn.core_navigation.GlobalNavigation
import app.tktn.feature_bookmarks.di.FeatureBookmarksNavigation
import org.koin.core.annotation.Single

@Single
class FeatureBookmarksNavigationImpl : GlobalNavigationImpl(), FeatureBookmarksNavigation {
}