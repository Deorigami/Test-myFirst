import androidx.compose.runtime.SideEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.cmp.template.RootApp
import com.cmp.template.deeplink.DeepLinkServer
import com.cmp.template.di.RootAppModule
import dev.theolm.rinku.Rinku
import io.github.orioneee.AxerTrayWindow
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
import java.awt.Desktop
import java.awt.Frame

fun main() {
    startKoin {
        modules(RootAppModule().module)
    }

    // Mutable reference updated once the Compose window is created.
    // Captured by the lambdas below — reads the live value at call time.
    var appWindow: java.awt.Window? = null

    fun bringToFront() {
        val win = appWindow ?: return
        if (win is Frame && win.state == Frame.ICONIFIED) {
            win.state = Frame.NORMAL   // un-minimise
        }
        win.isVisible = true
        win.toFront()
        win.requestFocus()
    }

    // ── Deep link: custom URI scheme (com.cmp.template://) ──────────────────
    // Works when the app is registered as URI scheme handler in the OS.
    if (Desktop.isDesktopSupported()) {
        val desktop = Desktop.getDesktop()
        if (desktop.isSupported(Desktop.Action.APP_OPEN_URI)) {
            desktop.setOpenURIHandler { event ->
                bringToFront()
                Rinku.handleDeepLink(event.uri.toString())
            }
        }
    }

    // ── Deep link: local HTTP server (http://localhost:54399/callback) ───────
    // Works without OS registration — singpass-callback redirects here for desktop.
    DeepLinkServer.start { url ->
        // Already dispatched to AWT thread inside DeepLinkServer
        bringToFront()
        Rinku.handleDeepLink(url)
    }

    application {
        AxerTrayWindow()
        Window(
            onCloseRequest = {
                DeepLinkServer.stop()
                exitApplication()
            },
            title = "CMP Multiplatform Template"
        ) {
            // Keep appWindow in sync with the actual Compose window handle.
            // 'window' is the ComposeWindow exposed by FrameWindowScope (the
            // lambda receiver of Window { }), no internal APIs needed.
            SideEffect { appWindow = window }

            RootApp()
        }
    }
}
