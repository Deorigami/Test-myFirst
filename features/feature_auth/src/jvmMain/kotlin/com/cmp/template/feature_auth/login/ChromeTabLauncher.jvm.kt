package com.cmp.template.feature_auth.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.awt.Desktop
import java.net.URI

private fun browseUrl(url: String) {
    if (Desktop.isDesktopSupported()) {
        val separator = if (url.contains("?")) "&" else "?"
        Desktop.getDesktop().browse(URI("$url${separator}platform=desktop"))
    }
}

@Composable
actual fun rememberChromeLauncher(): ChromeTabLauncher {
    return remember {
        object : ChromeTabLauncher {
            override fun launch(url: String) = browseUrl(url)
        }
    }
}

actual fun chromeLauncher(): ChromeTabLauncher = object : ChromeTabLauncher {
    override fun launch(url: String) = browseUrl(url)
}

