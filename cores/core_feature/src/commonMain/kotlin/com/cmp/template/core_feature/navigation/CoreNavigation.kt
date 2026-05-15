package com.cmp.template.core_feature.navigation
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
val LocalNavStack = compositionLocalOf<SnapshotStateList<NavKey>> { mutableStateListOf() }
