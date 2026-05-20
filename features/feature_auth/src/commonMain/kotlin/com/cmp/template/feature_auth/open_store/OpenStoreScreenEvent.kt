package com.cmp.template.feature_auth.open_store

import dev.theolm.rinku.DeepLink

interface OpenStoreScreenEvent {
	fun startSingpassAuthentication()
	fun onSingpassDeeplinkReceived(deepLink: DeepLink)
	fun accessStatusButtonAction()
}