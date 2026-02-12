package app.tktn.attendees_check.navigation

import app.tktn.feature_search.di.FeatureSearchNavigation
import org.koin.core.annotation.Single

@Single
class FeatureSearchNavigationImpl : GlobalNavigationImpl(), FeatureSearchNavigation {
}