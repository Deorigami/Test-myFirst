package com.cmp.template.feature_auth.login

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.SHARE_STATE_OFF
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import com.cmp.template.feature.auth.R
import com.cmp.template.theme.Color298CDE
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun Context.findActivity(): Activity? = when (this) {
    is Activity       -> this
    is ContextWrapper -> baseContext.findActivity()
    else              -> null
}

// ─────────────────────────────────────────────────────────────────────────────
//  File-private registry — populated automatically by rememberChromeLauncher().
//  chromeLauncher() reuses this so both paths stay in the same task.
// ─────────────────────────────────────────────────────────────────────────────
@Volatile private var _sharedLauncher: ActivityResultLauncher<Intent>? = null

// ────────────────────────────────────────────────────────────────────────────
//  Shared intent builder
// ─────────────────────────────────────────────────────────────────────────────
private fun buildTabIntent(context: Context, url: String): Intent {
    val activityContext = context.findActivity() ?: context
    val toolbarColor    = Color298CDE.toArgb()

    val density = activityContext.resources.displayMetrics.density
    val sizePx  = (24 * density).toInt().coerceAtLeast(1)
    val backArrowBitmap = ContextCompat.getDrawable(activityContext, R.drawable.arrow_back)
        ?.toBitmap(sizePx, sizePx)

    val colorParams = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(toolbarColor)
        .build()

    val intentBuilder = CustomTabsIntent.Builder()
        .setDefaultColorSchemeParams(colorParams)
        .setShowTitle(true)
        .setUrlBarHidingEnabled(true)
    intentBuilder.setShareState(SHARE_STATE_OFF)
    backArrowBitmap?.let { intentBuilder.setCloseButtonIcon(it) }

    val intent = intentBuilder.build().intent
    intent.data = url.toUri()
    intent.flags = intent.flags
        .and(Intent.FLAG_ACTIVITY_NEW_TASK.inv())
        .and(Intent.FLAG_ACTIVITY_NEW_DOCUMENT.inv())
    return intent
}

// ────────────────────────────────────────────────────────────────────────────
//  Composable path
// ─────────────────────────────────────────────────────────────────────────────
@Composable
actual fun rememberChromeLauncher(): ChromeTabLauncher {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { },
    )

    // Auto-register so chromeLauncher() can piggy-back on this launcher.
    // Unregistered when the Composable leaves composition.
    DisposableEffect(launcher) {
        _sharedLauncher = launcher
        onDispose { if (_sharedLauncher === launcher) _sharedLauncher = null }
    }

    return remember(launcher, context) {
        object : ChromeTabLauncher {
            override fun launch(url: String) {
                launcher.launch(buildTabIntent(context, url))
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  Non-Composable path — safe to call from ViewModels, use-cases, etc.
//  Reuses the ActivityResultLauncher registered by the active screen's
//  rememberChromeLauncher() call → stays in the same task (1 Recents entry).
//  Falls back to startActivity only when no screen is in the foreground.
// ─────────────────────────────────────────────────────────────────────────────
actual fun chromeLauncher(): ChromeTabLauncher = object : ChromeTabLauncher, KoinComponent {
    override fun launch(url: String) {
        val registered = _sharedLauncher
        val context: Context by inject()

        if (registered != null) {
            // Same-task path: reuse the screen's ActivityResultLauncher
            registered.launch(buildTabIntent(context, url))
        } else {
            // Fallback: no active screen — use startActivity with NEW_TASK
            val intent = buildTabIntent(context, url)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
