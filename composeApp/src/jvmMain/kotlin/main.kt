import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.cmp.template.RootApp
import com.cmp.template.di.RootAppModule
import io.github.orioneee.AxerTrayWindow
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
fun main() {
    startKoin {
        modules(RootAppModule().module)
    }
    application {
        AxerTrayWindow()
        Window(
            onCloseRequest = ::exitApplication,
            title = "CMP Multiplatform Template"
        ) {
            RootApp()
        }
    }
}
