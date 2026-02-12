package app.tktn.core_feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
import androidx.savedstate.compose.serialization.serializers.SnapshotStateListSerializer
import kotlinx.serialization.serializer

val LocalNavStack = compositionLocalOf<SnapshotStateList<NavKey>> { mutableStateListOf() }

@Composable
inline fun <reified T : NavKey> rememberNavBackStack(vararg elements: T): MutableList<T> {
    return rememberSerializable(serializer = SnapshotStateListSerializer(serializer())) { mutableStateListOf(*elements) }
}
