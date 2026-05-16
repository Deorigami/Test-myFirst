package com.cmp.template.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cmp.template.components.Res
import com.cmp.template.components.Poppins_Black
import com.cmp.template.components.Poppins_Bold
import com.cmp.template.components.Poppins_ExtraBold
import com.cmp.template.components.Poppins_ExtraLight
import com.cmp.template.components.Poppins_Light
import com.cmp.template.components.Poppins_Medium
import com.cmp.template.components.Poppins_Regular
import com.cmp.template.components.Poppins_SemiBold
import com.cmp.template.components.Poppins_Thin
import org.jetbrains.compose.resources.Font

// ─────────────────────────────────────────────
//  Poppins font family
// ─────────────────────────────────────────────

@Composable
fun poppinsFontFamily() = FontFamily(
    Font(Res.font.Poppins_Thin,       FontWeight.Thin),
    Font(Res.font.Poppins_ExtraLight, FontWeight.ExtraLight),
    Font(Res.font.Poppins_Light,      FontWeight.Light),
    Font(Res.font.Poppins_Regular,    FontWeight.Normal),
    Font(Res.font.Poppins_Medium,     FontWeight.Medium),
    Font(Res.font.Poppins_SemiBold,   FontWeight.SemiBold),
    Font(Res.font.Poppins_Bold,       FontWeight.Bold),
    Font(Res.font.Poppins_ExtraBold,  FontWeight.ExtraBold),
    Font(Res.font.Poppins_Black,      FontWeight.Black),
)

// ─────────────────────────────────────────────
//  Material 3 typography scale — Poppins
//
//  Mapping rationale:
//    Display   → Normal   (expressive, let size do the work)
//    Headline  → SemiBold (strong section headers)
//    Title     → SemiBold / Medium
//    Body      → Normal   (comfortable reading)
//    Label     → Medium   (compact UI labels)
// ─────────────────────────────────────────────

@Composable
internal fun appTypography(): Typography {
    val poppins = poppinsFontFamily()
    return Typography(
        displayLarge  = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Normal,   fontSize = 57.sp, lineHeight = 64.sp, letterSpacing = (-0.25).sp),
        displayMedium = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Normal,   fontSize = 45.sp, lineHeight = 52.sp, letterSpacing = 0.sp),
        displaySmall  = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Normal,   fontSize = 36.sp, lineHeight = 44.sp, letterSpacing = 0.sp),

        headlineLarge  = TextStyle(fontFamily = poppins, fontWeight = FontWeight.SemiBold, fontSize = 32.sp, lineHeight = 40.sp, letterSpacing = 0.sp),
        headlineMedium = TextStyle(fontFamily = poppins, fontWeight = FontWeight.SemiBold, fontSize = 28.sp, lineHeight = 36.sp, letterSpacing = 0.sp),
        headlineSmall  = TextStyle(fontFamily = poppins, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, lineHeight = 32.sp, letterSpacing = 0.sp),

        titleLarge  = TextStyle(fontFamily = poppins, fontWeight = FontWeight.SemiBold, fontSize = 22.sp, lineHeight = 28.sp, letterSpacing = 0.sp),
        titleMedium = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Medium,   fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.15.sp),
        titleSmall  = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Medium,   fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.1.sp),

        bodyLarge  = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.5.sp),
        bodyMedium = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.25.sp),
        bodySmall  = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.4.sp),

        labelLarge  = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.1.sp),
        labelMedium = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.5.sp),
        labelSmall  = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 16.sp, letterSpacing = 0.5.sp),
    )
}

