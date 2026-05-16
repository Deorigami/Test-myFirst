package com.cmp.template.feature_auth.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Composable
actual fun rememberChromeLauncher(): ChromeTabLauncher {
    return remember {
        object : ChromeTabLauncher {
            override fun launch(url: String) {
                NSURL.URLWithString(url)?.let { nsUrl ->
                    UIApplication.sharedApplication.openURL(nsUrl)
                }
            }
        }
    }
}

