package com.cmp.template.feature_auth.login

/** Platform-agnostic launcher that opens a URL in the best available browser. */
interface ChromeTabLauncher {
    fun launch(url: String)
}

/**
 * Returns a remembered [ChromeTabLauncher] backed by the platform implementation.
 * Must be called from a Composable scope.
 * Preferred for UI code — uses ActivityResultLauncher on Android (single Recents entry).
 */
@androidx.compose.runtime.Composable
expect fun rememberChromeLauncher(): ChromeTabLauncher

/**
 * Returns a [ChromeTabLauncher] that can be called from any scope (ViewModel, service, etc.).
 * On Android this uses the application context via Koin — may produce a separate Recents
 * entry due to Chrome's singleTask manifest declaration.
 * Prefer [rememberChromeLauncher] from a Composable screen when possible.
 */
expect fun chromeLauncher(): ChromeTabLauncher
