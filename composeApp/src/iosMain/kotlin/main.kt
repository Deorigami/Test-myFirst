import androidx.compose.ui.window.ComposeUIViewController
import com.cmp.template.RootApp
import com.cmp.template.di.RootAppModule
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
fun MainViewController() = ComposeUIViewController(
    configure = {
        startKoin {
            modules(RootAppModule().module)
        }
    }
) { RootApp() }
