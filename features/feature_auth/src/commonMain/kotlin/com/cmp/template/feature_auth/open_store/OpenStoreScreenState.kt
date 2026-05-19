package com.cmp.template.feature_auth.open_store
data class OpenStoreScreenState(
    val isLoading: Boolean = false,
    val accessStatus: Boolean? = null,
    val accessStatusCountdown: Int = 0
)