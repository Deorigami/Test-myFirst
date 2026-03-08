import androidx.compose.ui.window.ComposeUIViewController
import app.tktn.attendees_check.RootApp
import app.tktn.attendees_check.di.RootAppModule
import app.tktn.attendees_check.di.roomDatabaseModule
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
	startKoin {
		modules(RootAppModule().module, roomDatabaseModule)
	}
	return ComposeUIViewController { RootApp() }
}
