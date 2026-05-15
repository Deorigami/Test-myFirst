import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.cmp.template.RootApp
import com.cmp.template.di.RootAppModule
import kotlinx.browser.document
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        modules(RootAppModule().module)
    }
    ComposeViewport(document.body!!) {
        RootApp()
    }
}
