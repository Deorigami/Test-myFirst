package com.cmp.template.theme

import androidx.compose.ui.unit.dp

// ═══════════════════════════════════════════════════════════════
//  ICOS Atomic Design System — Design Tokens
//  Auto-translated from icos-atomic.css (Last modified: 2026-05-15)
// ═══════════════════════════════════════════════════════════════

// ─────────────────────────────────────────────
//  Spacing  (--space-N)
// ─────────────────────────────────────────────
object IcosSpacing {
    val s0  =  0.dp   // --space-0:  0px
    val s1  =  2.dp   // --space-1:  2px
    val s2  =  4.dp   // --space-2:  4px
    val s3  =  6.dp   // --space-3:  6px
    val s4  =  8.dp   // --space-4:  8px
    val s5  = 10.dp   // --space-5:  10px
    val s6  = 12.dp   // --space-6:  12px
    val s7  = 14.dp   // --space-7:  14px
    val s8  = 16.dp   // --space-8:  16px
    val s9  = 20.dp   // --space-9:  20px
    val s10 = 25.dp   // --space-10: 25px
    val s11 = 40.dp   // --space-11: 40px
    val s12 = 55.dp   // --space-12: 55px
    val s13 = 60.dp   // --space-13: 60px
    val s14 = 65.dp   // --space-14: 65px
    val s15 = 85.dp   // --space-15: 85px
}

// ─────────────────────────────────────────────
//  Border Radius  (--radius-N)
// ─────────────────────────────────────────────
object IcosRadius {
    val xs   =  2.dp  // --radius-xs:   2px
    val sm   =  4.dp  // --radius-sm:   4px
    val md   =  6.dp  // --radius-md:   6px
    val lg   =  8.dp  // --radius-lg:   8px
    val xl   = 10.dp  // --radius-xl:   10px
    val x2l  = 15.dp  // --radius-2xl:  15px
    val x3l  = 20.dp  // --radius-3xl:  20px
    val x4l  = 30.dp  // --radius-4xl:  30px
    val x5l  = 40.dp  // --radius-5xl:  40px
    val pill = 9999.dp // --radius-pill: 9999px (full pill)
}

// ─────────────────────────────────────────────
//  Stroke / Border Widths  (--stroke-N)
// ─────────────────────────────────────────────
object IcosStroke {
    val thin    = 0.5.dp  // --stroke-thin:    0.5px
    val default = 1.dp    // --stroke-default: 1px
    val medium  = 1.5.dp  // --stroke-medium:  1.5px
    val thick   = 2.dp    // --stroke-thick:   2px
    val heavy   = 3.dp    // --stroke-heavy:   3px
}

// ─────────────────────────────────────────────
//  Opacities  (--opacity-N)
// ─────────────────────────────────────────────
object IcosOpacity {
    const val o15  = 0.15f  // --opacity-15
    const val o25  = 0.25f  // --opacity-25
    const val o40  = 0.40f  // --opacity-40
    const val o50  = 0.50f  // --opacity-50
    const val o70  = 0.70f  // --opacity-70
    const val full = 1.00f  // fully opaque
}

// ─────────────────────────────────────────────
//  Shadows  (--shadow-N)
//
//  Compose does not have a direct CSS box-shadow equivalent.
//  Use these parameters with Modifier.shadow() or custom
//  DrawScope / graphicsLayer implementations.
//
//  --shadow-sm   : 0 0 4px 0 rgba(106,77,187,0.12)
//  --shadow-md   : 0 3px 8px 0 rgba(0,0,0,0.15)
//  --shadow-lg   : 0 4px 14px 4px rgba(0,0,0,0.08)
//  --shadow-soft : 0 4px 3px 0 rgba(208,208,208,0.52)
// ─────────────────────────────────────────────
object IcosShadow {
    /** 0 0 4px 0 rgba(106,77,187,0.12) — subtle purple tint */
    val sm   = ShadowSpec(offsetX = 0.dp,  offsetY = 0.dp,  blurRadius = 4.dp,  spreadRadius = 0.dp,  colorArgb = 0x1F6A4DBB)
    /** 0 3px 8px 0 rgba(0,0,0,0.15) — standard card shadow */
    val md   = ShadowSpec(offsetX = 0.dp,  offsetY = 3.dp,  blurRadius = 8.dp,  spreadRadius = 0.dp,  colorArgb = 0x26000000)
    /** 0 4px 14px 4px rgba(0,0,0,0.08) — elevated panel shadow */
    val lg   = ShadowSpec(offsetX = 0.dp,  offsetY = 4.dp,  blurRadius = 14.dp, spreadRadius = 4.dp,  colorArgb = 0x14000000)
    /** 0 4px 3px 0 rgba(208,208,208,0.52) — soft grey shadow */
    val soft = ShadowSpec(offsetX = 0.dp,  offsetY = 4.dp,  blurRadius = 3.dp,  spreadRadius = 0.dp,  colorArgb = 0x85D0D0D0.toInt())
}

/**
 * Holds CSS box-shadow parameters as Compose-friendly Dp values.
 * Use [blurRadius] with Modifier.shadow() or pass to a custom draw call.
 * [colorArgb] is the shadow color as a packed ARGB Int (e.g. 0x26000000).
 */
data class ShadowSpec(
    val offsetX:      androidx.compose.ui.unit.Dp,
    val offsetY:      androidx.compose.ui.unit.Dp,
    val blurRadius:   androidx.compose.ui.unit.Dp,
    val spreadRadius: androidx.compose.ui.unit.Dp,
    val colorArgb:    Int,
)



