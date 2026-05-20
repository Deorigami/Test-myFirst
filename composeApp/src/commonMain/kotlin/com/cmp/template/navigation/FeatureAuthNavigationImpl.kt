package com.cmp.template.navigation

import com.cmp.template.feature_auth.di.FeatureAuthNavigation
import org.koin.core.annotation.Single

@Single
class FeatureAuthNavigationImpl : GlobalNavigationImpl(), FeatureAuthNavigation {
	override fun navigateToSingpassLogin() {

	}
}