package com.cmp.template.feature_auth.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

private fun openUrl(url: String) {
    NSURL.URLWithString(url)?.let { nsUrl ->
        UIApplication.sharedApplication.openURL(nsUrl)
    }
}

@Composable
actual fun rememberChromeLauncher(): ChromeTabLauncher {
    return remember {
        object : ChromeTabLauncher {
            override fun launch(url: String) = openUrl(url)
        }
    }
}

actual fun chromeLauncher(): ChromeTabLauncher = object : ChromeTabLauncher {
    override fun launch(url: String) = openUrl(url)
}

