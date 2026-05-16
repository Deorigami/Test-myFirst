package com.cmp.template.feature_auth.login

sealed interface SingpassLoginScreenEvent {
    data object FetchAuthUrl : SingpassLoginScreenEvent
    data object Retry        : SingpassLoginScreenEvent
}

