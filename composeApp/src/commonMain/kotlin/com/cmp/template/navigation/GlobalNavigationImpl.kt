package com.cmp.template.navigation

import androidx.compose.runtime.snapshots.Snapshot
import com.cmp.template.core_navigation.GlobalNavigation

abstract class GlobalNavigationImpl : GlobalNavigation {

    /**
     * Pops the top entry from the back-stack.
     * Uses [removeLastOrNull] instead of [removeLast] so it is a no-op
     * when only one entry remains — preventing an empty-stack crash in NavDisplay.
     */
    override fun back() {
        val stack = NavDestinations.backStack
        if (stack.size > 1) stack.removeLastOrNull()
    }

    /**
     * Clears the entire back-stack and replaces it with [NavDestinations.Home].
     *
     * [Snapshot.withMutableSnapshot] batches both mutations into a single
     * atomic snapshot write, so NavDisplay never observes the stack in an
     * empty intermediate state (which would cause a crash).
     */
    override fun navigateToHome() {
        Snapshot.withMutableSnapshot {
            NavDestinations.backStack.clear()
            NavDestinations.backStack.add(NavDestinations.Home)
        }
    }

    // TODO: Add navigate methods for additional destinations here
}
