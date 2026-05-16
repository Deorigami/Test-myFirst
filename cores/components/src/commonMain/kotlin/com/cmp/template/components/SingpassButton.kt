package com.cmp.template.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmp.template.theme.SingpassRed
import org.jetbrains.compose.resources.painterResource

// ─── Singpass brand tokens ──────────────────────────────────────────────────
private val SingpassTextDark = Color(0xFF1A1A1A)
private val SingpassCorner   = RoundedCornerShape(8.dp)
private val SingpassMinH     = 48.dp
private val SingpassIconSize = 24.dp

/**
 * The two official Singpass button styles defined in the Singpass button
 * guidelines for developers and designers.
 *
 * - [Red]   – Singpass-red filled button; white icon and white text.
 *             Use on light-coloured page backgrounds.
 * - [White] – White (outlined) button with Singpass-red border; coloured
 *             icon and styled "singpass" text.
 *             Use on dark or Singpass-red backgrounds.
 */
enum class SingpassButtonStyle { Red, White }

/**
 * An official-spec Singpass login button.
 *
 * Layout: [ S-mark icon | "Login with" "s" in red + "ingpass" ]
 *
 * @param onClick   Called when the button is tapped.
 * @param modifier  Standard Compose modifier.
 * @param style     [SingpassButtonStyle.Red] (default) or [SingpassButtonStyle.White].
 * @param label     Prefix text before "Singpass". Defaults to `"Login with "`.
 * @param iconSize  Size of the Singpass face mark. Defaults to 24.dp.
 * @param enabled   Whether the button responds to clicks.
 */
@Composable
fun SingpassButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: SingpassButtonStyle = SingpassButtonStyle.Red,
    label: String = "Log in with ",
    iconSize: Dp = SingpassIconSize,
    enabled: Boolean = true,
) {
    when (style) {
        SingpassButtonStyle.Red -> SingpassRedButton(
            onClick = onClick,
            modifier = modifier,
            label = label,
            iconSize = iconSize,
            enabled = enabled,
        )
        SingpassButtonStyle.White -> SingpassWhiteButton(
            onClick = onClick,
            modifier = modifier,
            label = label,
            iconSize = iconSize,
            enabled = enabled,
        )
    }
}

// ─── Red variant ─────────────────────────────────────────────────────────────

@Composable
private fun SingpassRedButton(
    onClick: () -> Unit,
    modifier: Modifier,
    label: String,
    iconSize: Dp,
    enabled: Boolean,
) {
    Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = SingpassMinH),
        enabled = enabled,
        shape = SingpassCorner,
        colors = ButtonDefaults.buttonColors(
            containerColor         = SingpassRed,
            contentColor           = Color.White,
            disabledContainerColor = SingpassRed.copy(alpha = 0.4f),
            disabledContentColor   = Color.White.copy(alpha = 0.6f),
        ),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            horizontal = 20.dp,
            vertical   = 0.dp,
        ),
    ) {
        SingpassButtonContent(
            label    = label,
            // On the red background the red square of the icon blends in —
            // only the white person mark is visible. Tint makes both paths white.
            iconTint = ColorFilter.tint(Color.White),
            textColor = Color.White,
            // On a filled red button both "s" and "ingpass" are the same white
        )
    }
}

// ─── White variant ───────────────────────────────────────────────────────────

@Composable
private fun SingpassWhiteButton(
    onClick: () -> Unit,
    modifier: Modifier,
    label: String,
    iconSize: Dp,
    enabled: Boolean,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = SingpassMinH),
        enabled = enabled,
        shape = SingpassCorner,
        border = BorderStroke(
            width = 1.5.dp,
            color = Color(0xFFC8C9CC)
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor         = Color.White,
            contentColor           = SingpassTextDark,
            disabledContainerColor = Color.White,
            disabledContentColor   = SingpassTextDark.copy(alpha = 0.4f),
        ),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            horizontal = 20.dp,
            vertical   = 0.dp,
        ),
    ) {
        SingpassButtonContent(
            label    = label,
            // Full-colour icon — red square visible against white background
            iconTint          = null,
            textColor         = SingpassTextDark,
            // Styled "singpass": red "s", dark "ingpass"
        )
    }
}

// ─── Shared content ──────────────────────────────────────────────────────────

@Composable
private fun SingpassButtonContent(
    label: String,
    iconTint: ColorFilter?,
    textColor: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally),
        modifier = Modifier.height(IntrinsicSize.Max)
    ) {

        // ── Label + styled "singpass" wordmark ───────────────────────────
        Text(
            text = buildAnnotatedString {
                // Prefix: "Login with " or custom label
                withStyle(SpanStyle(color = textColor, fontWeight = FontWeight.Bold)) {
                    append(label)
                }
            },
            fontSize    = 16.sp,
            letterSpacing = 0.sp,
            modifier = Modifier
        )

        Image(
            painter             = painterResource(Res.drawable.singpass_logo_fullcolours),
            contentDescription  = "Singpass",
            modifier            = Modifier.fillMaxHeight(0.45f).offset(y = Dp(2f)),
            colorFilter         = iconTint
        )
    }
}


