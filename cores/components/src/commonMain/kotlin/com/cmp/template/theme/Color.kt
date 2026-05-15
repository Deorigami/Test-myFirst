package com.cmp.template.theme

import androidx.compose.ui.graphics.Color

// ─────────────────────────────────────────────
//  Raw palette – extracted from UI screenshots
// ─────────────────────────────────────────────

/** Primary brand blue – used for filled buttons, active tabs, links */
val AppBlue          = Color(0xFF254F9E)
/** Lighter tint of the brand blue – hover / pressed states */
val AppBlueMedium    = Color(0xFF3A6BC4)
/** Very light blue – primary containers */
val AppBlueLight     = Color(0xFFD6E3FF)
/** Deepest blue – text on light blue containers */
val AppBlueDark      = Color(0xFF001947)

/** Secondary blue-gray – supporting UI elements */
val AppGrayBlue      = Color(0xFF565F8A)
val AppGrayBlueLight = Color(0xFFDCE1FF)
val AppGrayBlueDark  = Color(0xFF121C42)

/** Orange accent – arrows, progress indicators, highlights */
val AppOrange        = Color(0xFFF5973D)
val AppOrangeLight   = Color(0xFFFFDDB7)
val AppOrangeDark    = Color(0xFF2B1700)

/** Singpass red – used for Singpass brand / critical errors */
val SingpassRed      = Color(0xFFE8003D)
val SingpassRedLight = Color(0xFFFFD9DF)
val SingpassRedDark  = Color(0xFF5C0017)

/** Neutral dark – outer app background (kiosk dark surround) */
val AppOuterDark     = Color(0xFF1B1B1B)

/** Standard background-white card */
val AppWhite         = Color(0xFFFFFFFF)
/** Slight off-white background */
val AppSurface       = Color(0xFFFAFAFF)
/** Light surface variant for containers */
val AppSurfaceVariant = Color(0xFFE1E2F0)

/** Text – primary */
val AppTextPrimary   = Color(0xFF1A1C26)
/** Text – secondary / caption */
val AppTextSecondary = Color(0xFF757575)
/** Text – disabled / hint */
val AppTextDisabled  = Color(0xFFBBBBBB)

/** Outline / divider */
val AppOutline       = Color(0xFF757685)
val AppOutlineVariant = Color(0xFFC4C5D6)

// ─────────────────────────────────────────────
//  Material 3 Light color roles
// ─────────────────────────────────────────────

val PrimaryLight                  = AppBlue
val OnPrimaryLight                = Color(0xFFFFFFFF)
val PrimaryContainerLight         = AppBlueLight
val OnPrimaryContainerLight       = AppBlueDark

val SecondaryLight                = AppGrayBlue
val OnSecondaryLight              = Color(0xFFFFFFFF)
val SecondaryContainerLight       = AppGrayBlueLight
val OnSecondaryContainerLight     = AppGrayBlueDark

val TertiaryLight                 = Color(0xFF7A5200)   // warm brown-orange for light theme
val OnTertiaryLight               = Color(0xFFFFFFFF)
val TertiaryContainerLight        = AppOrangeLight
val OnTertiaryContainerLight      = AppOrangeDark

val ErrorLight                    = Color(0xFFB3261E)
val OnErrorLight                  = Color(0xFFFFFFFF)
val ErrorContainerLight           = Color(0xFFF9DEDC)
val OnErrorContainerLight         = Color(0xFF410E0B)

val BackgroundLight               = AppSurface
val OnBackgroundLight             = AppTextPrimary

val SurfaceLight                  = AppWhite
val OnSurfaceLight                = AppTextPrimary
val SurfaceVariantLight           = AppSurfaceVariant
val OnSurfaceVariantLight         = Color(0xFF454654)

val OutlineLight                  = AppOutline
val OutlineVariantLight           = AppOutlineVariant
val ScrimLight                    = Color(0xFF000000)

val InverseSurfaceLight           = Color(0xFF2E2F3D)
val InverseOnSurfaceLight         = Color(0xFFF2EFFF)
val InversePrimaryLight           = Color(0xFFADC7FF)

val SurfaceDimLight               = Color(0xFFDADAEA)
val SurfaceBrightLight            = AppSurface
val SurfaceContainerLowestLight   = Color(0xFFFFFFFF)
val SurfaceContainerLowLight      = Color(0xFFF4F4FF)
val SurfaceContainerLight         = AppSurfaceVariant
val SurfaceContainerHighLight     = Color(0xFFDBDCEB)
val SurfaceContainerHighestLight  = Color(0xFFD5D6E5)

// ─────────────────────────────────────────────
//  Material 3 Dark color roles
// ─────────────────────────────────────────────

val PrimaryDark                   = Color(0xFFADC7FF)
val OnPrimaryDark                 = Color(0xFF002E6E)
val PrimaryContainerDark          = Color(0xFF003B8F)
val OnPrimaryContainerDark        = AppBlueLight

val SecondaryDark                 = Color(0xFFBAC4F5)
val OnSecondaryDark               = Color(0xFF272F59)
val SecondaryContainerDark        = Color(0xFF3E4770)
val OnSecondaryContainerDark      = AppGrayBlueLight

val TertiaryDark                  = Color(0xFFFFB866)
val OnTertiaryDark                = Color(0xFF482900)
val TertiaryContainerDark         = Color(0xFF673D00)
val OnTertiaryContainerDark       = AppOrangeLight

val ErrorDark                     = Color(0xFFF2B8B5)
val OnErrorDark                   = Color(0xFF601410)
val ErrorContainerDark            = Color(0xFF8C1D18)
val OnErrorContainerDark          = Color(0xFFF9DEDC)

val BackgroundDark                = Color(0xFF12131E)
val OnBackgroundDark              = Color(0xFFE4E1F5)

val SurfaceDark                   = Color(0xFF12131E)
val OnSurfaceDark                 = Color(0xFFE4E1F5)
val SurfaceVariantDark            = Color(0xFF454654)
val OnSurfaceVariantDark          = AppOutlineVariant

val OutlineDark                   = Color(0xFF8F90A1)
val OutlineVariantDark            = Color(0xFF454654)
val ScrimDark                     = Color(0xFF000000)

val InverseSurfaceDark            = Color(0xFFE4E1F5)
val InverseOnSurfaceDark          = Color(0xFF2E2F3D)
val InversePrimaryDark            = AppBlue

val SurfaceDimDark                = Color(0xFF12131E)
val SurfaceBrightDark             = Color(0xFF383844)
val SurfaceContainerLowestDark    = Color(0xFF0D0E19)
val SurfaceContainerLowDark       = Color(0xFF1A1C26)
val SurfaceContainerDark          = Color(0xFF1E1F2A)
val SurfaceContainerHighDark      = Color(0xFF292A35)
val SurfaceContainerHighestDark   = Color(0xFF333440)

