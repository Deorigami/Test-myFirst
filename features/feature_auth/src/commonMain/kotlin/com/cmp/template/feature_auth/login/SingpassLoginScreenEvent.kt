package com.cmp.template.feature_auth.login

interface SingpassLoginScreenEvent {
    fun onRetry()
    fun onDeeplinkReceived(parameters: Map<String, String>)
}
