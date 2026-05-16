package com.cmp.template.feature_auth.login

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Composable
actual fun rememberChromeLauncher(): ChromeTabLauncher {
    val context = LocalContext.current
    return remember(context) {
        object : ChromeTabLauncher {
            override fun launch(url: String) {
                CustomTabsIntent.Builder()
                    .setShowTitle(false)
                    .setUrlBarHidingEnabled(true)
                    .build()
                    .launchUrl(context, url.toUri())
            }
        }
    }
}

