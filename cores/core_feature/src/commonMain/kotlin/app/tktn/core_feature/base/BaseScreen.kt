package app.tktn.core_feature.base


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import kotlinx.serialization.Serializable

@Serializable
abstract class BaseScreen {
    open val headerTitle: String? = null
    open val hasLiveTimeDisplay: Boolean = true
    open val hasHeader: Boolean = true
    open val backPressEnabled: Boolean = false

    @Composable
    protected open fun ComposeContent() {
    }

    abstract val pageName: String?

    @Composable
    fun ComposableScreen() {
        ComposeContent()
    }
}

@Composable
fun DismissibleKeyboardContainer(
    content: @Composable () -> Unit
) {
    // Get the keyboard controller and focus manager
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    // Create a box that fills the screen and handles clicks
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // Remove ripple effect
            ) {
                // Hide keyboard and clear focus when clicking outside
                keyboardController?.hide()
                focusManager.clearFocus()
            }
    ) {
        content()
    }
}