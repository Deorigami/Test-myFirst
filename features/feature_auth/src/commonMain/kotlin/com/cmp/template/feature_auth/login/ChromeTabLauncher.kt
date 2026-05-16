package com.cmp.template.feature_auth.login

/** Platform-agnostic launcher that opens a URL in the best available browser. */
interface ChromeTabLauncher {
    fun launch(url: String)
}

/** Returns a remembered [ChromeTabLauncher] backed by the platform implementation. */
@androidx.compose.runtime.Composable
expect fun rememberChromeLauncher(): ChromeTabLauncher

