package com.cmp.template.feature_auth.login

data class SingpassLoginScreenState(
    val authUrl: String? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
)

