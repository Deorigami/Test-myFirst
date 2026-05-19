package com.cmp.template.feature_auth.login

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.SHARE_STATE_OFF
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import com.cmp.template.feature.auth.R
import com.cmp.template.theme.Color298CDE

fun Context.findActivity(): Activity? = when (this) {
    is Activity       -> this
    is ContextWrapper -> baseContext.findActivity()
    else              -> null
}

@Composable
actual fun rememberChromeLauncher(): ChromeTabLauncher {
    val context      = LocalContext.current
    val toolbarColor = Color298CDE.toArgb()

    // KEY FIX: Using ActivityResultLauncher (= startActivityForResult internally).
    // Chrome's CCT Activity declares android:launchMode="singleTask" in its own manifest,
    // so a plain startActivity always creates a new task → two cards in Recents.
    // startActivityForResult bypasses that by keeping the CCT associated with our task.
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { /* no result needed */ },
    )

    return remember(launcher, context, toolbarColor) {
        object : ChromeTabLauncher {
            override fun launch(url: String) {
                val activityContext = context.findActivity() ?: context

                // Scale close-button to exactly 24 dp to avoid parcel-size crash
                val density = activityContext.resources.displayMetrics.density
                val sizePx  = (24 * density).toInt().coerceAtLeast(1)
                val backArrowBitmap = ContextCompat.getDrawable(
                    activityContext,
                    R.drawable.arrow_back,
                )?.toBitmap(sizePx, sizePx)

                val colorParams = CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(toolbarColor)
                    .build()

                val intentBuilder = CustomTabsIntent.Builder()
                    .setDefaultColorSchemeParams(colorParams)
                    .setShowTitle(true)
                    .setUrlBarHidingEnabled(true)
                intentBuilder.setShareState(SHARE_STATE_OFF)
                backArrowBitmap?.let { intentBuilder.setCloseButtonIcon(it) }

//                intentBuilder.setStartAnimations(activityContext, R.anim.slide_in_right, R.anim.slide_out_left)
//                intentBuilder.setExitAnimations(activityContext, R.anim.slide_in_left, R.anim.slide_out_right)

                val customTabsIntent = intentBuilder.build()

                // Set the URL on the intent directly (launchUrl is NOT used —
                // we launch via ActivityResultLauncher to stay in the same task)
                customTabsIntent.intent.data = url.toUri()

                // Also strip the flags as a belt-and-suspenders measure
                customTabsIntent.intent.flags = customTabsIntent.intent.flags
                    .and(Intent.FLAG_ACTIVITY_NEW_TASK.inv())
                    .and(Intent.FLAG_ACTIVITY_NEW_DOCUMENT.inv())

                launcher.launch(customTabsIntent.intent)
            }
        }
    }
}