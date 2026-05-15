package com.cmp.template.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.materialkolor.rememberDynamicColorScheme
internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }
@Composable
internal fun AppTheme(
    content: @Composable () -> Unit
) {
    val systemIsDark = isSystemInDarkTheme()
    val isDarkState = remember(systemIsDark) { mutableStateOf(systemIsDark) }
    CompositionLocalProvider(
        LocalThemeIsDark provides isDarkState
    ) {
        val isDark by isDarkState
        SystemAppearance(!isDark)
        val colorScheme = rememberDynamicColorScheme(Color(0xFF48A6A7), isDark)
        MaterialTheme(
            colorScheme = colorScheme,
            content = { Surface(content = content) }
        )
    }
}
@Composable
internal expect fun SystemAppearance(isDark: Boolean)
