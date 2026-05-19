package com.cmp.template.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ═══════════════════════════════════════════════════════════════
//  ICOS Freestyle TextStyles
//
//  Naming convention : {Weight}{Size}
//    Weight → Regular | Medium | SemiBold | Bold | Black
//    Size   → actual sp value (11 | 12 | 14 | 16 | 18 | 20 |
//              22 | 24 | 26 | 28 | 30 | 32 | 36 | 38 | 40 |
//              48 | 64 | 96)
//
//  Line-height = fontSize × lh-normal (1.17), rounded to nearest sp.
//
//  Usage:
//    val ts = icosTextStyles()
//    Text("Hello", style = ts.Bold24)
//    Text("Sub",   style = ts.Medium16)
// ═══════════════════════════════════════════════════════════════

data class IcosTextStyles(
    // ── 11sp ────────────────────────────────────────────────
    val Regular11  : TextStyle,
    val Medium11   : TextStyle,
    val SemiBold11 : TextStyle,
    val Bold11     : TextStyle,
    val Black11    : TextStyle,

    // ── 12sp ────────────────────────────────────────────────
    val Regular12  : TextStyle,
    val Medium12   : TextStyle,
    val SemiBold12 : TextStyle,
    val Bold12     : TextStyle,
    val Black12    : TextStyle,

    // ── 14sp ────────────────────────────────────────────────
    val Regular14  : TextStyle,
    val Medium14   : TextStyle,
    val SemiBold14 : TextStyle,
    val Bold14     : TextStyle,
    val Black14    : TextStyle,

    // ── 16sp ────────────────────────────────────────────────
    val Regular16  : TextStyle,
    val Medium16   : TextStyle,
    val SemiBold16 : TextStyle,
    val Bold16     : TextStyle,
    val Black16    : TextStyle,

    // ── 18sp ────────────────────────────────────────────────
    val Regular18  : TextStyle,
    val Medium18   : TextStyle,
    val SemiBold18 : TextStyle,
    val Bold18     : TextStyle,
    val Black18    : TextStyle,

    // ── 20sp ────────────────────────────────────────────────
    val Regular20  : TextStyle,
    val Medium20   : TextStyle,
    val SemiBold20 : TextStyle,
    val Bold20     : TextStyle,
    val Black20    : TextStyle,

    // ── 22sp ────────────────────────────────────────────────
    val Regular22  : TextStyle,
    val Medium22   : TextStyle,
    val SemiBold22 : TextStyle,
    val Bold22     : TextStyle,
    val Black22    : TextStyle,

    // ── 24sp ────────────────────────────────────────────────
    val Regular24  : TextStyle,
    val Medium24   : TextStyle,
    val SemiBold24 : TextStyle,
    val Bold24     : TextStyle,
    val Black24    : TextStyle,

    // ── 26sp ────────────────────────────────────────────────
    val Regular26  : TextStyle,
    val Medium26   : TextStyle,
    val SemiBold26 : TextStyle,
    val Bold26     : TextStyle,
    val Black26    : TextStyle,

    // ── 28sp ────────────────────────────────────────────────
    val Regular28  : TextStyle,
    val Medium28   : TextStyle,
    val SemiBold28 : TextStyle,
    val Bold28     : TextStyle,
    val Black28    : TextStyle,

    // ── 30sp ────────────────────────────────────────────────
    val Regular30  : TextStyle,
    val Medium30   : TextStyle,
    val SemiBold30 : TextStyle,
    val Bold30     : TextStyle,
    val Black30    : TextStyle,

    // ── 32sp ────────────────────────────────────────────────
    val Regular32  : TextStyle,
    val Medium32   : TextStyle,
    val SemiBold32 : TextStyle,
    val Bold32     : TextStyle,
    val Black32    : TextStyle,

    // ── 36sp ────────────────────────────────────────────────
    val Regular36  : TextStyle,
    val Medium36   : TextStyle,
    val SemiBold36 : TextStyle,
    val Bold36     : TextStyle,
    val Black36    : TextStyle,

    // ── 38sp ────────────────────────────────────────────────
    val Regular38  : TextStyle,
    val Medium38   : TextStyle,
    val SemiBold38 : TextStyle,
    val Bold38     : TextStyle,
    val Black38    : TextStyle,

    // ── 40sp ────────────────────────────────────────────────
    val Regular40  : TextStyle,
    val Medium40   : TextStyle,
    val SemiBold40 : TextStyle,
    val Bold40     : TextStyle,
    val Black40    : TextStyle,

    // ── 48sp ────────────────────────────────────────────────
    val Regular48  : TextStyle,
    val Medium48   : TextStyle,
    val SemiBold48 : TextStyle,
    val Bold48     : TextStyle,
    val Black48    : TextStyle,

    // ── 64sp ────────────────────────────────────────────────
    val Regular64  : TextStyle,
    val Medium64   : TextStyle,
    val SemiBold64 : TextStyle,
    val Bold64     : TextStyle,
    val Black64    : TextStyle,

    // ── 96sp ────────────────────────────────────────────────
    val Regular96  : TextStyle,
    val Medium96   : TextStyle,
    val SemiBold96 : TextStyle,
    val Bold96     : TextStyle,
    val Black96    : TextStyle,
)

/**
 * Returns an [IcosTextStyles] instance using the system default font (Roboto on Android).
 * Line heights follow the ICOS `lh-normal` multiplier (×1.17).
 */
@Composable
fun rememberIcosTextStyles(): IcosTextStyles {
    return remember {
        fun style(size: Int, weight: FontWeight): TextStyle {
            val lh = kotlin.math.round(size * 1.17f)
            return TextStyle(
                fontFamily    = FontFamily.Default,
                fontWeight    = weight,
                fontSize      = size.sp,
                lineHeight    = lh.sp,
                letterSpacing = 0.sp,
                color = Color383B46
            )
        }

        val R  = FontWeight.Normal
        val M  = FontWeight.Medium
        val SB = FontWeight.SemiBold
        val B  = FontWeight.Bold
        val BK = FontWeight.Black

        IcosTextStyles(
            Regular11  = style(11, R),  Medium11   = style(11, M),  SemiBold11 = style(11, SB),  Bold11  = style(11, B),  Black11  = style(11, BK),
            Regular12  = style(12, R),  Medium12   = style(12, M),  SemiBold12 = style(12, SB),  Bold12  = style(12, B),  Black12  = style(12, BK),
            Regular14  = style(14, R),  Medium14   = style(14, M),  SemiBold14 = style(14, SB),  Bold14  = style(14, B),  Black14  = style(14, BK),
            Regular16  = style(16, R),  Medium16   = style(16, M),  SemiBold16 = style(16, SB),  Bold16  = style(16, B),  Black16  = style(16, BK),
            Regular18  = style(18, R),  Medium18   = style(18, M),  SemiBold18 = style(18, SB),  Bold18  = style(18, B),  Black18  = style(18, BK),
            Regular20  = style(20, R),  Medium20   = style(20, M),  SemiBold20 = style(20, SB),  Bold20  = style(20, B),  Black20  = style(20, BK),
            Regular22  = style(22, R),  Medium22   = style(22, M),  SemiBold22 = style(22, SB),  Bold22  = style(22, B),  Black22  = style(22, BK),
            Regular24  = style(24, R),  Medium24   = style(24, M),  SemiBold24 = style(24, SB),  Bold24  = style(24, B),  Black24  = style(24, BK),
            Regular26  = style(26, R),  Medium26   = style(26, M),  SemiBold26 = style(26, SB),  Bold26  = style(26, B),  Black26  = style(26, BK),
            Regular28  = style(28, R),  Medium28   = style(28, M),  SemiBold28 = style(28, SB),  Bold28  = style(28, B),  Black28  = style(28, BK),
            Regular30  = style(30, R),  Medium30   = style(30, M),  SemiBold30 = style(30, SB),  Bold30  = style(30, B),  Black30  = style(30, BK),
            Regular32  = style(32, R),  Medium32   = style(32, M),  SemiBold32 = style(32, SB),  Bold32  = style(32, B),  Black32  = style(32, BK),
            Regular36  = style(36, R),  Medium36   = style(36, M),  SemiBold36 = style(36, SB),  Bold36  = style(36, B),  Black36  = style(36, BK),
            Regular38  = style(38, R),  Medium38   = style(38, M),  SemiBold38 = style(38, SB),  Bold38  = style(38, B),  Black38  = style(38, BK),
            Regular40  = style(40, R),  Medium40   = style(40, M),  SemiBold40 = style(40, SB),  Bold40  = style(40, B),  Black40  = style(40, BK),
            Regular48  = style(48, R),  Medium48   = style(48, M),  SemiBold48 = style(48, SB),  Bold48  = style(48, B),  Black48  = style(48, BK),
            Regular64  = style(64, R),  Medium64   = style(64, M),  SemiBold64 = style(64, SB),  Bold64  = style(64, B),  Black64  = style(64, BK),
            Regular96  = style(96, R),  Medium96   = style(96, M),  SemiBold96 = style(96, SB),  Bold96  = style(96, B),  Black96  = style(96, BK),
        )
    }
}
