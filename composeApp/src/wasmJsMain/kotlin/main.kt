import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import app.tktn.attendees_check.RootApp
import app.tktn.attendees_check.di.RootAppModule
import kotlinx.browser.document
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val body = document.body ?: return
    startKoin {
        modules(RootAppModule().module)
    }
    ComposeViewport(body) {
        RootApp()
    }
}
