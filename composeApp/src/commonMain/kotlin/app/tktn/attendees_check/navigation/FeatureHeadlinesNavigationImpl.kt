package app.tktn.attendees_check.navigation

import app.tktn.feature_headlines.di.FeatureHeadlinesNavigation
import org.koin.core.annotation.Single

@Single
class FeatureHeadlinesNavigationImpl : GlobalNavigationImpl(), FeatureHeadlinesNavigation {
}