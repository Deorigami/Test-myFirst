package com.cmp.template.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ═══════════════════════════════════════════════════════════════
//  ICOS Atomic Design System — Typography
//  Font family: system default (Roboto on Android)
//
//  M3 role → ICOS composite preset mapping:
//    displayLarge   → heading-hero  : 96sp  SemiBold  lh≈112sp
//    displayMedium  → heading-1     : 40sp  SemiBold  lh=30sp (tight)
//    displaySmall   → heading-2     : 38sp  Bold      lh≈44sp
//    headlineLarge  → heading-3     : 32sp  Medium    lh≈37sp
//    headlineMedium → heading-4     : 28sp  Medium    lh≈33sp
//    headlineSmall  → heading-5     : 24sp  SemiBold  lh≈28sp
//    titleLarge     → heading-6     : 20sp  Bold      lh≈23sp
//    titleMedium    → subtitle-1    : 18sp  Medium    lh≈21sp
//    titleSmall     → subtitle-2    : 16sp  SemiBold  lh≈19sp
//    bodyLarge      → body-1        : 16sp  Regular   lh≈19sp
//    bodyMedium     → body-2        : 14sp  Regular   lh≈16sp
//    bodySmall      → caption       : 12sp  Medium    lh≈14sp
//    labelLarge     → body-2-medium : 14sp  Medium    lh≈16sp
//    labelMedium    → caption       : 12sp  Medium    lh≈14sp
//    labelSmall     → overline      : 11sp  Medium    lh≈13sp
// ═══════════════════════════════════════════════════════════════

internal fun appTypography() = Typography(
    // ── Display ─────────────────────────────────────────────
    displayLarge  = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.SemiBold, fontSize = 96.sp, lineHeight = 112.sp, letterSpacing = 0.sp),
    displayMedium = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.SemiBold, fontSize = 40.sp, lineHeight =  30.sp, letterSpacing = 0.sp),
    displaySmall  = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold,     fontSize = 38.sp, lineHeight =  44.sp, letterSpacing = 0.sp),

    // ── Headline ────────────────────────────────────────────
    headlineLarge  = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium,   fontSize = 32.sp, lineHeight = 37.sp, letterSpacing = 0.sp),
    headlineMedium = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium,   fontSize = 28.sp, lineHeight = 33.sp, letterSpacing = 0.sp),
    headlineSmall  = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, lineHeight = 28.sp, letterSpacing = 0.sp),

    // ── Title ───────────────────────────────────────────────
    titleLarge  = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold,     fontSize = 20.sp, lineHeight = 23.sp, letterSpacing = 0.sp),
    titleMedium = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium,   fontSize = 18.sp, lineHeight = 21.sp, letterSpacing = 0.15.sp),
    titleSmall  = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, lineHeight = 19.sp, letterSpacing = 0.1.sp),

    // ── Body ─────────────────────────────────────────────
    bodyLarge  = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 19.sp, letterSpacing = 0.5.sp),
    bodyMedium = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 16.sp, letterSpacing = 0.25.sp),
    bodySmall  = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 14.sp, letterSpacing = 0.4.sp),

    // ── Label ───────────────────────────────────────────────
    labelLarge  = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 16.sp, letterSpacing = 0.1.sp),
    labelMedium = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 14.sp, letterSpacing = 0.5.sp),
    labelSmall  = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 13.sp, letterSpacing = 0.5.sp),
)
