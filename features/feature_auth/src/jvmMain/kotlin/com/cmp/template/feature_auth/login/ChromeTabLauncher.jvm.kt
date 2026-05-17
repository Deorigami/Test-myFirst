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
                    // Append platform=desktop so singpass-callback routes to
                    // the local DeepLinkServer on http://localhost:54399
                    val separator = if (url.contains("?")) "&" else "?"
                    val fullUrl = "$url${separator}platform=desktop"
                    Desktop.getDesktop().browse(URI(fullUrl))
                }
            }
        }
    }
}

