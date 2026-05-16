package com.cmp.template.feature_auth.login

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberChromeLauncher(): ChromeTabLauncher {
    val context = LocalContext.current
    return remember(context) {
        object : ChromeTabLauncher {
            override fun launch(url: String) {
                CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .setUrlBarHidingEnabled(true)
                    .build()
                    .launchUrl(context, Uri.parse(url))
            }
        }
    }
}

