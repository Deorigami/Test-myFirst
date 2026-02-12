import androidx.compose.ui.window.ComposeUIViewController
import app.tktn.attendees_check.RootApp
import app.tktn.attendees_check.di.RootAppModule
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
	startKoin {
		modules(RootAppModule().module)
	}
	return ComposeUIViewController { RootApp() }
}
