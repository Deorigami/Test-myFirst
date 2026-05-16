package com.cmp.template.feature_auth.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.awt.Desktop
import java.net.URI

@Composable
actual fun rememberChromeLauncher(): ChromeTabLauncher {
    return remember {
        object : ChromeTabLauncher {
            override fun launch(url: String) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(URI(url))
                }
            }
        }
    }
}

